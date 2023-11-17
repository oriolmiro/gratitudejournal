package org.insbaixcamp.gratitude.journal.daily.ui.insight;


import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.insbaixcamp.gratitude.journal.daily.R;
import org.insbaixcamp.gratitude.journal.daily.databinding.FragmentInsightBinding;
import org.insbaixcamp.gratitude.journal.daily.tools.SettingsManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class InsightFragment extends Fragment {
    int count_entries;
    int feelingCount1 = 0;
    int feelingCount2 = 0;
    int feelingCount3 = 0;
    int feelingCount4 = 0;
    int feelingCount5 = 0;
    private SharedPreferences sharedPref;
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
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String fechaActual = dateFormat.format(currentDate);

        String date_first_time = settingsManagerInstance.getDateFirstTime();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference journalEntriesRef = database.getReference("journal/" + userId + "/");
        Date storedDate = null;
        Date storedDate2 = null;
        try {
            storedDate = dateFormat.parse(date_first_time);
            storedDate2 = dateFormat.parse(fechaActual);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long differenceInMillis = storedDate2.getTime() - storedDate.getTime();
        long differenceInDays = (TimeUnit.MILLISECONDS.toDays(differenceInMillis)) + 1;
        binding.tvDaysApplicationResult.setText(String.valueOf(differenceInDays) + getString(R.string.days));
        journalEntriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count_entries = (int) dataSnapshot.getChildrenCount();
                binding.tvTotalEntriesResult.setText(String.valueOf(count_entries) + getString(R.string.entries));

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
                if (count_entries != 0) {
                    // Calcular y mostrar el porcentaje para feelingCount1
                    double porcentaje1 = (double) feelingCount1 / count_entries * 100;
                    if (porcentaje1 != 0.0) {
                        binding.tvFeeling1Result.setText(String.format("%.2f%%", porcentaje1));
                    } else {
                        binding.tvFeeling1Result.setText("N/A");
                    }

                    // Calcular y mostrar el porcentaje para feelingCount2
                    double porcentaje2 = (double) feelingCount2 / count_entries * 100;
                    if (porcentaje2 != 0.0) {
                        binding.tvFeeling2Result.setText(String.format("%.2f%%", porcentaje2));
                    } else {
                        binding.tvFeeling2Result.setText("N/A");
                    }

                    // Calcular y mostrar el porcentaje para feelingCount3
                    double porcentaje3 = (double) feelingCount3 / count_entries * 100;
                    if (porcentaje3 != 0.0) {
                        binding.tvFeeling3Result.setText(String.format("%.2f%%", porcentaje3));
                    } else {
                        binding.tvFeeling3Result.setText("N/A");
                    }

                    // Calcular y mostrar el porcentaje para feelingCount4
                    double porcentaje4 = (double) feelingCount4 / count_entries * 100;
                    if (porcentaje4 != 0.0) {
                        binding.tvFeeling4Result.setText(String.format("%.2f%%", porcentaje4));
                    } else {
                        binding.tvFeeling4Result.setText("N/A");
                    }

                    // Calcular y mostrar el porcentaje para feelingCount5
                    double porcentaje5 = (double) feelingCount5 / count_entries * 100;
                    if (porcentaje5 != 0.0) {
                        binding.tvFeeling5Result.setText(String.format("%.2f%%", porcentaje5));
                    } else {
                        binding.tvFeeling5Result.setText("N/A");
                    }
                } else {
                    // Si h es 0, establecer los textos en los TextView como "N/A"
                    binding.tvFeeling1Result.setText("N/A");
                    binding.tvFeeling2Result.setText("N/A");
                    binding.tvFeeling3Result.setText("N/A");
                    binding.tvFeeling4Result.setText("N/A");
                    binding.tvFeeling5Result.setText("N/A");
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