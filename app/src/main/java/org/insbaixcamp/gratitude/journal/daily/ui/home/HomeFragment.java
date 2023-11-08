// HomeFragment.java
package org.insbaixcamp.gratitude.journal.daily.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        RecyclerView recyclerView = binding.rcvJournalEntry;
        List<JournalEntry> journalEntries = obtenerJournalEntries(); // Llama a tu método para obtener los datos
        EntryAdapter adapter = new EntryAdapter(journalEntries, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            Log.d("DEBUG_TAG", "Fragment: " + fragment.getClass().getSimpleName());
        }
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

    private List<JournalEntry> obtenerJournalEntries() {
        List<JournalEntry> entries = new ArrayList<>();

        // Agrega tus entradas de ejemplo o datos reales aquí
        entries.add(new JournalEntry("2023-10-27", "Día soleado", "Hoy fue un hermoso día soleado.", R.drawable.llorando));
        entries.add(new JournalEntry("2023-10-26", "Reunión importante", "Tuve una reunión importante en la oficina.", R.drawable.indiferente));

        // Agrega más entradas según sea necesario

        return entries;
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
