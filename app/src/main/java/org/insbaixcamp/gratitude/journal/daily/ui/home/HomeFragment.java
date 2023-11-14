package org.insbaixcamp.gratitude.journal.daily.ui.home;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.insbaixcamp.gratitude.journal.daily.R;
import org.insbaixcamp.gratitude.journal.daily.databinding.FragmentHomeBinding;
import org.insbaixcamp.gratitude.journal.daily.model.JournalEntry;
import org.insbaixcamp.gratitude.journal.daily.tools.SettingsManager;
import org.insbaixcamp.gratitude.journal.daily.ui.journal.JournalFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment implements EntryAdapter.OnItemClickListener {

    private FragmentHomeBinding binding;
    private TextView tvDateToday;
    private TextView tvMessageDay;
    private SettingsManager settingsManager;

    private EntryAdapter adapter;
    private RecyclerView recyclerView;
    List<JournalEntry> journalArray;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tvDateToday = root.findViewById(R.id.tv_date_today);
        tvMessageDay = root.findViewById(R.id.tv_message_day);

        settingsManager = new SettingsManager(getContext());
        settingsManager.saveUserToFirebase();

        actualizarFecha();
        mostrarSaludoSegunHora();
        compararFechaDeHoyConUltimoEntry();

        // Recupera el RecyclerView del archivo de diseño
        recyclerView = binding.rcvJournalEntry;

        // Configura el RecyclerView con un LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializa el adaptador con una lista vacía
        journalArray = new ArrayList<>();

        // Llama a tu método para obtener los datos desde Firebase
        obtenerJournalEntriesDesdeFirebase();

        // Inicializa el adaptador con los datos obtenidos
        adapter = new EntryAdapter(journalArray, getContext());

        // Agrega el listener al adaptador
        adapter.setOnItemClickListener(this);

        // Asigna el adaptador al RecyclerView
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged(); // Notifica al RecyclerView que se han realizado cambios en los datos

        binding.btnWriteEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_navigation_journal);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void obtenerJournalEntriesDesdeFirebase() {
        SettingsManager settingsManagerInstance = new SettingsManager(getContext());
        String userId = settingsManagerInstance.getUserId();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference journalEntriesRef = database.getReference("journal/" + userId + "/");

        journalEntriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                journalArray.clear();
                for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                    JournalEntry entry = entrySnapshot.getValue(JournalEntry.class);
                    journalArray.add(entry);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Manejar errores de lectura desde Firebase
            }
        });
    }

    private void actualizarFecha() {
        // Obtener la fecha actual y formatearla
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd, MMMM yyyy");
        String currentDate = dateFormat.format(new Date());

        // Establecer la fecha en el TextView
        tvDateToday.setText(currentDate);
    }

    private void mostrarSaludoSegunHora() {
        // Obtener la fecha actual y formatearla
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
        int currentHour = Integer.parseInt(dateFormat.format(new Date()));

        // Obtener el nombre del usuario desde SettingsManager
        String userName = settingsManager.getUserName();

        String greeting;
        if (currentHour >= 0 && currentHour < 12) {
            greeting = "Buenos días";
        } else if (currentHour >= 12 && currentHour < 18) {
            greeting = "Buenas tardes";
        } else {
            greeting = "Buenas noches";
        }

        // Crear el mensaje combinando el saludo y el nombre del usuario
        String message = greeting + ", " + userName;

        // Establecer la fecha y el mensaje en los TextViews
        tvMessageDay.setText(message);
    }

    private void compararFechaDeHoyConUltimoEntry() {
        obtenerUltimoJournalEntryDesdeFirebase(new FirebaseCallback() {
            @Override
            public void onCallback(JournalEntry ultimoEntry) {
                if (ultimoEntry != null) {
                    String fechaUltimoEntry = ultimoEntry.getDate();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String fechaHoy = dateFormat.format(new Date());

                    if (fechaUltimoEntry != null && fechaHoy != null && fechaUltimoEntry.equals(fechaHoy)) {
                        binding.btnWriteEntry.setVisibility(View.GONE);
                    } else {
                        binding.btnWriteEntry.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void obtenerUltimoJournalEntryDesdeFirebase(FirebaseCallback callback) {
        String userId = settingsManager.getUserId();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference journalEntriesRef = database.getReference("journal/" + userId);

        journalEntriesRef.orderByChild("timestamp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            DataSnapshot ultimoEntrySnapshot = dataSnapshot.getChildren().iterator().next();
                            JournalEntry ultimoEntry = ultimoEntrySnapshot.getValue(JournalEntry.class);
                            callback.onCallback(ultimoEntry);
                        } else {
                            callback.onCallback(null);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        callback.onCallback(null);
                    }
                });
    }

    @Override
    public void onItemClick(int position) {
        JournalEntry selectedEntry = journalArray.get(position);

        Bundle bundle = new Bundle();
        bundle.putParcelable("selectedEntry", (Parcelable) selectedEntry);

        // Utiliza el NavController para navegar hacia JournalFragment
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_navigation_home_to_navigation_journal, bundle);
    }



    private interface FirebaseCallback {
        void onCallback(JournalEntry ultimoEntry);
    }
}
