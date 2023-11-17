package org.insbaixcamp.gratitude.journal.daily.ui.insight;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.insbaixcamp.gratitude.journal.daily.R;
import org.insbaixcamp.gratitude.journal.daily.databinding.FragmentHomeBinding;
import org.insbaixcamp.gratitude.journal.daily.databinding.FragmentInsightBinding;
import org.insbaixcamp.gratitude.journal.daily.tools.SettingsManager;
import org.insbaixcamp.gratitude.journal.daily.ui.home.HomeFragment;


public class InsightFragment extends Fragment {
    int h;
    int feelingCount1 = 0;
    int feelingCount2 = 0;
    int feelingCount3 = 0;
    int feelingCount4 = 0;
    int feelingCount5 = 0;
    private FragmentInsightBinding binding;
    private InsightViewModel mViewModel;

    public static InsightFragment newInstance() {
        return new InsightFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentInsightBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SettingsManager settingsManagerInstance = new SettingsManager(getContext());
        String userId = settingsManagerInstance.getUserId();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference journalEntriesRef = database.getReference("journal/" + userId + "/");

        journalEntriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                h = (int) dataSnapshot.getChildrenCount();
                binding.textView2.setText(String.valueOf(h));

                for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                    Integer feelingValue = entrySnapshot.child("feeling").getValue(Integer.class);

                    if (feelingValue != null) {
                        switch (feelingValue) {
                            case 1:
                                feelingCount1++;
                                break;
                            case 2:
                                feelingCount2++;
                                break;
                            case 3:
                                feelingCount3++;
                                break;
                            case 4:
                                feelingCount4++;
                                break;
                            case 5:
                                feelingCount5++;
                                break;
                        }
                    }
                }

                // Verificar si h es 0 antes de calcular y mostrar los porcentajes
                if (h != 0) {
                    // Calcular y mostrar el porcentaje para feelingCount1
                    double porcentaje1 = (double) feelingCount1 / h * 100;
                    binding.textView7.setText(String.format("%.2f%%", porcentaje1));

                    // Calcular y mostrar el porcentaje para feelingCount2
                    double porcentaje2 = (double) feelingCount2 / h * 100;
                    binding.textView6.setText(String.format("%.2f%%", porcentaje2));

                    // Calcular y mostrar el porcentaje para feelingCount3
                    double porcentaje3 = (double) feelingCount3 / h * 100;
                    binding.textView8.setText(String.format("%.2f%%", porcentaje3));

                    // Calcular y mostrar el porcentaje para feelingCount4
                    double porcentaje4 = (double) feelingCount4 / h * 100;
                    binding.textView9.setText(String.format("%.2f%%", porcentaje4));

                    // Calcular y mostrar el porcentaje para feelingCount5
                    double porcentaje5 = (double) feelingCount5 / h * 100;
                    binding.textView10.setText(String.format("%.2f%%", porcentaje5));
                } else {
                    // Si h es 0, establecer los textos en los TextView como "N/A"
                    binding.textView7.setText("Feeling 1: N/A");
                    binding.textView6.setText("Feeling 2: N/A");
                    binding.textView8.setText("Feeling 3: N/A");
                    binding.textView9.setText("Feeling 4: N/A");
                    binding.textView10.setText("Feeling 5: N/A");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled if needed
            }
        });

        return root;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InsightViewModel.class);
        // TODO: Use the ViewModel
    }

}