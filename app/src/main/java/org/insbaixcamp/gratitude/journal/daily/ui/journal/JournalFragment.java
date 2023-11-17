package org.insbaixcamp.gratitude.journal.daily.ui.journal;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.insbaixcamp.gratitude.journal.daily.R;
import org.insbaixcamp.gratitude.journal.daily.databinding.FragmentHomeBinding;
import org.insbaixcamp.gratitude.journal.daily.databinding.FragmentJournalBinding;
import org.insbaixcamp.gratitude.journal.daily.model.JournalEntry;
import org.insbaixcamp.gratitude.journal.daily.model.Quote;
import org.insbaixcamp.gratitude.journal.daily.model.User;
import org.insbaixcamp.gratitude.journal.daily.tools.QuoteCallback;
import org.insbaixcamp.gratitude.journal.daily.tools.QuotesManager;
import org.insbaixcamp.gratitude.journal.daily.tools.SettingsManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JournalFragment extends Fragment {
    private String selectedEntryFecha;
    private String userId;
    private JournalViewModel mViewModel;
    private FragmentJournalBinding binding;
    private int numeroGuardado = 0;
    private ImageButton botonAnimado = null;
    private JournalEntry journalEntry;

    public static JournalFragment newInstance() {
        return new JournalFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentJournalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Verifica si hay datos en el Bundle
        if (getArguments() != null) {
            JournalEntry selectedEntry = getArguments().getParcelable("selectedEntry");

            // Actualiza tu interfaz de usuario con los datos de selectedEntry
            if (selectedEntry != null) {
                binding.tvEntryDate.setText(selectedEntry.getDate());
                selectedEntryFecha = selectedEntry.getDate();
                binding.tvEntryPhrase.setText(selectedEntry.getPhrase());
                binding.tvAuthor.setText(selectedEntry.getAutor());
                binding.tvPhraseSun.setText(selectedEntry.getTitleSun());
                binding.teMessageSun.setText(selectedEntry.getMessageSun());
                binding.tvPhraseMoon.setText(selectedEntry.getTitleMoon());
                binding.teMessageMoon.setText(selectedEntry.getMessageMoon());
                int feelingNumber = selectedEntry.getFeeling();
                if (feelingNumber == 1) {
                    aplicarAnimacion(binding.ibFeeling1);
                    numeroGuardado = 1;
                    botonAnimado = binding.ibFeeling1;
                } else if (feelingNumber == 2) {
                    aplicarAnimacion(binding.ibFeeling2);
                    numeroGuardado = 2;
                    botonAnimado = binding.ibFeeling2;
                } else if (feelingNumber == 3) {
                    aplicarAnimacion(binding.ibFeeling3);
                    numeroGuardado = 3;
                    botonAnimado = binding.ibFeeling3;
                } else if (feelingNumber == 4) {
                    aplicarAnimacion(binding.ibFeeling4);
                    numeroGuardado = 4;
                    botonAnimado = binding.ibFeeling4;
                } else if (feelingNumber == 5) {
                    aplicarAnimacion(binding.ibFeeling5);
                    numeroGuardado = 5;
                    botonAnimado = binding.ibFeeling5;
                }


            }
        }  else  {
            new QuotesManager().getQuoteOfTheDay(new QuoteCallback() {
                @Override
                public void onCallback(Quote quote) {
                    binding.tvEntryPhrase.setText(quote.getPhrase());
                    binding.tvAuthor.setText(quote.getAuthor());
                }

                @Override
                public void onError(String error) {
                    Log.e("JournalFragmentFirebaseQuote", error);
                }
            });
        }
        Log.d("TAGf", "Fecha actual: " + selectedEntryFecha);

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String fechaActual = dateFormat.format(currentDate);

        if (selectedEntryFecha == null || selectedEntryFecha.isEmpty()) {
            binding.tvEntryDate.setText(formatDateString(fechaActual));
        } else {
            binding.tvEntryDate.setText(formatDateString(selectedEntryFecha));
        }





        binding.ivShareIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                configurarBotonCompartir();
            }
        });

        SettingsManager settingsManagerInstance = new SettingsManager(getContext());
        String userId = settingsManagerInstance.getUserId();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference journalEntriesRef = database.getReference("journal/" + userId + "/" + fechaActual + "/");

        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
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
                String phrase = binding.tvEntryPhrase.getText().toString();
                String autor = binding.tvAuthor.getText().toString();
                String titleSun = binding.tvPhraseSun.getText().toString();
                String messageSun = binding.teMessageSun.getText().toString();
                String titleMoon = binding.tvPhraseMoon.getText().toString();
                String messageMoon = binding.teMessageMoon.getText().toString();

                if (selectedEntryFecha != null && !selectedEntryFecha.trim().isEmpty()) {
                    // Modo de edición: Actualiza el elemento existente
                    if (journalEntry == null) {
                        journalEntry = new JournalEntry(); // Inicializa el objeto si es nulo
                    }
                    journalEntry.setDate(selectedEntryFecha);
                    journalEntry.setPhrase(phrase);
                    journalEntry.setAutor(autor);
                    journalEntry.setTitleSun(titleSun);
                    journalEntry.setMessageSun(messageSun);
                    journalEntry.setTitleMoon(titleMoon);
                    journalEntry.setMessageMoon(messageMoon);
                    journalEntry.setFeeling(numeroGuardado);
                } else {
                    // Modo de creación: Crea un nuevo elemento
                    journalEntry = new JournalEntry(
                            fechaActual, phrase, autor, titleSun, messageSun, titleMoon, messageMoon, numeroGuardado
                    );
                }

                if (messageMoon.isEmpty() && messageSun.isEmpty() && numeroGuardado == 0) {
                    mostrarToast("Completa los mensajes y selecciona un ícono.");
                } else if (messageMoon.isEmpty() && numeroGuardado == 0) {
                    mostrarToast("Completa el mensaje de la luna y selecciona un ícono.");
                } else if (messageSun.isEmpty() && numeroGuardado == 0) {
                    mostrarToast("Completa el mensaje del sol y selecciona un ícono.");
                } else if (messageMoon.isEmpty() && messageSun.isEmpty()) {
                    mostrarToast("Completa los mensajes del sol y la luna.");
                } else if (messageMoon.isEmpty()) {
                    mostrarToast("Completa el mensaje de la luna.");
                } else if (messageSun.isEmpty()) {
                    mostrarToast("Completa el mensaje del sol.");
                } else if (numeroGuardado == 0) {
                    mostrarToast("Selecciona un ícono.");
                } else {
                    // Realiza la operación correspondiente (creación o actualización)
                    DatabaseReference journalEntriesRef = FirebaseDatabase.getInstance()
                            .getReference("journal/" + userId + "/" + (selectedEntryFecha != null ? selectedEntryFecha : fechaActual) + "/");
                    journalEntriesRef.setValue(journalEntry);

                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.navigation_home);
                }
            }
        });



        return root;
    }

    // Método para mostrar Toast
    private void mostrarToast(String mensaje) {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
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

    private void configurarBotonCompartir() {

        // Obtén la frase, el autor y el enlace que deseas compartir
        String frase = binding.tvEntryPhrase.getText().toString();
        String autor = binding.tvAuthor.getText().toString();
        String enlace = "https://play.google.com/store/apps/details?id=org.insbaixcamp.gratitude.journal.daily";


        // Construir el contenido a compartir
        String contenidoACompartir = frase + "\n" + "Autor: " + autor + "\n" + "Enlace: " + enlace + "\n" + "Envidado desde: " + getString(R.string.app_name);

        // Crear un Intent para compartir
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, contenidoACompartir);

        // Iniciar la actividad de compartir
        startActivity(Intent.createChooser(intent, "Compartir con"));

    }

    private CharSequence formatDateString(String fechaActual) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date date = sdf.parse(fechaActual);

            return DateFormat.getDateInstance().format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Puedes manejar el error según tus necesidades
        }
    }

}