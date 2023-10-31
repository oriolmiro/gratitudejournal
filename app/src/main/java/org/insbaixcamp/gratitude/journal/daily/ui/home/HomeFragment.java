// HomeFragment.java
package org.insbaixcamp.gratitude.journal.daily.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.insbaixcamp.gratitude.journal.daily.R;
import org.insbaixcamp.gratitude.journal.daily.databinding.FragmentHomeBinding;
import org.insbaixcamp.gratitude.journal.daily.model.JournalEntry;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView = binding.rcvJournalEntry;
        List<JournalEntry> journalEntries = obtenerJournalEntries(); // Llama a tu método para obtener los datos
        EntryAdapter adapter = new EntryAdapter(journalEntries, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
binding.btnWriteEntry.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.navigation_journal);
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

}
