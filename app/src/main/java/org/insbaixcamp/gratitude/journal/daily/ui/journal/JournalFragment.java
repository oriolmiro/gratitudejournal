package org.insbaixcamp.gratitude.journal.daily.ui.journal;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.insbaixcamp.gratitude.journal.daily.R;
import org.insbaixcamp.gratitude.journal.daily.databinding.FragmentHomeBinding;
import org.insbaixcamp.gratitude.journal.daily.databinding.FragmentJournalBinding;
import org.insbaixcamp.gratitude.journal.daily.model.JournalEntry;
import org.insbaixcamp.gratitude.journal.daily.tools.SettingsManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JournalFragment extends Fragment {

    private JournalViewModel mViewModel;
    private FragmentJournalBinding binding;
    private int numeroGuardado = 0;
    private ImageButton botonAnimado = null;
    public static JournalFragment newInstance() {
        return new JournalFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentJournalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String fechaActual = dateFormat.format(currentDate);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = null;
        try {
            date = sdf.parse(fechaActual);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        CharSequence formatteDate = DateFormat.getDateInstance().format(date);
        binding.tvEntryDate.setText(formatteDate);


        SettingsManager settingsManagerInstance = new SettingsManager(getContext());
        String userId = settingsManagerInstance.getUserId();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference journalEntriesRef = database.getReference("journal/" + userId + "/"+ fechaActual + "/");

        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
        for(Fragment fragment : fragments){
            Log.d("DEBUG_TAG", "Fragment: " + fragment.getClass().getSimpleName());
        }
        // Botón 1
        binding.ibFeeling1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (botonAnimado != binding.ibFeeling1) {
                    // Cancela la animación en el botón anterior si es diferente
                    if (botonAnimado != null) {
                        botonAnimado.clearAnimation();
                    }
                    aplicarAnimacion(binding.ibFeeling1);
                    numeroGuardado = 1;
                    botonAnimado = binding.ibFeeling1;
                }
            }
        });

// Botón 2
        binding.ibFeeling2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (botonAnimado != binding.ibFeeling2) {
                    if (botonAnimado != null) {
                        botonAnimado.clearAnimation();
                    }
                    aplicarAnimacion(binding.ibFeeling2);
                    numeroGuardado = 2;
                    botonAnimado = binding.ibFeeling2;
                }
            }
        });

// Botón 3
        binding.ibFeeling3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (botonAnimado != binding.ibFeeling3) {
                    if (botonAnimado != null) {
                        botonAnimado.clearAnimation();
                    }
                    aplicarAnimacion(binding.ibFeeling3);
                    numeroGuardado = 3;
                    botonAnimado = binding.ibFeeling3;
                }
            }
        });

// Botón 4
        binding.ibFeeling4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (botonAnimado != binding.ibFeeling4) {
                    if (botonAnimado != null) {
                        botonAnimado.clearAnimation();
                    }
                    aplicarAnimacion(binding.ibFeeling4);
                    numeroGuardado = 4;
                    botonAnimado = binding.ibFeeling4;
                }
            }
        });

// Botón 5
        binding.ibFeeling5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (botonAnimado != binding.ibFeeling5) {
                    if (botonAnimado != null) {
                        botonAnimado.clearAnimation();
                    }
                    aplicarAnimacion(binding.ibFeeling5);
                    numeroGuardado = 5;
                    botonAnimado = binding.ibFeeling5;
                }
            }
        });
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prhase = binding.tvEntryPhrase.getText().toString();
                String autor = binding.tvAuthor.getText().toString();
                String titleSun = binding.tvPhraseSun.getText().toString();
                String messageSun = binding.teMessageSun.getText().toString();
                String titleMoon = binding.tvPhraseMoon.getText().toString();
                String messageMoon = binding.teMessageMoon.getText().toString();
                JournalEntry journalEntry = new JournalEntry(fechaActual,prhase, autor, titleSun, messageSun,titleMoon,messageMoon,numeroGuardado);
                journalEntriesRef.setValue(journalEntry);
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.navigation_home);


            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(JournalViewModel.class);

    }
    private void aplicarAnimacion(ImageButton imageButton) {
        // Carga la animación desde el recurso XML
        Animation animacion = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        animacion.setFillAfter(true);
        // Aplica la animación al ImageButton específico
        imageButton.startAnimation(animacion);
    }

}