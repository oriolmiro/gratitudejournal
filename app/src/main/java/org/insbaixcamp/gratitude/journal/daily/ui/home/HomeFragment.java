// HomeFragment.java
package org.insbaixcamp.gratitude.journal.daily.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.insbaixcamp.gratitude.journal.daily.MainActivity;
import org.insbaixcamp.gratitude.journal.daily.R;
import org.insbaixcamp.gratitude.journal.daily.databinding.FragmentHomeBinding;
import org.insbaixcamp.gratitude.journal.daily.model.JournalEntry;
import org.insbaixcamp.gratitude.journal.daily.tools.SettingsManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TextView tvDateToday;
    private TextView tvMessageDay;
    private SettingsManager settingsManager;

    private EntryAdapter adapter;
    private RecyclerView recyclerView;
    List<JournalEntry> journalArray;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tvDateToday = root.findViewById(R.id.tv_date_today);
        tvMessageDay = root.findViewById(R.id.tv_message_day);

        settingsManager = new SettingsManager(getContext());

        actualizarFecha();
        mostrarSaludoSegunHora();

        // Recupera el RecyclerView del archivo de diseño
        recyclerView = binding.rcvJournalEntry;


        // Configura el RecyclerView con un LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Inicializa el adaptador con una lista vacía
        journalArray = new ArrayList<>();

        // Llama a tu método para obtener los datos desde Firebase
        obtenerJournalEntriesDesdeFirebase();

        binding.btnWriteEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
                //NavController navController = navHostFragment.getNavController();
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_navigation_journal);
                //MainActivity.navController.navigate(R.id.action_navigation_home_to_navigation_journal);
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

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String fechaActual = dateFormat.format(currentDate);

        SettingsManager settingsManagerInstance = new SettingsManager(getContext());
        String userId = settingsManagerInstance.getUserId();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference journalEntriesRef = database.getReference("journal/" + userId + "/");

        journalEntriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                journalArray.clear();
                for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                    // Usar DataSnapshot para obtener los valores directamente

                    JournalEntry entry = entrySnapshot.getValue(JournalEntry.class);

                    journalArray.add(entry);
                }

                // Inicializa el adaptador con los datos obtenidos
                adapter = new EntryAdapter(journalArray, getContext());

                // Asigna el adaptador al RecyclerView
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged(); // Notifica al RecyclerView que se han realizado cambios en los datos
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
        String currentDate = dateFormat.format(new Date());

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

}
