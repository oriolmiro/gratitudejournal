package org.insbaixcamp.gratitude.journal.daily.ui.onboarding;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.insbaixcamp.gratitude.journal.daily.R;
import org.insbaixcamp.gratitude.journal.daily.databinding.FragmentOnboarding3Binding;

public class Onboarding3Fragment extends Fragment {

    private FragmentOnboarding3Binding binding;
    private TextInputEditText usuari;
    private Button btnNext2;

    public static Onboarding3Fragment newInstance() {
        return new Onboarding3Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOnboarding3Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        usuari = (TextInputEditText) binding.tfOnboarding3.getEditText();
        btnNext2 = binding.btnOnboarding3;
        btnNext2.setEnabled(false);

        usuari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Aquí verificamos si el campo de entrada tiene texto y habilitamos o deshabilitamos el botón "Next"
                boolean isInputEmpty = s.toString().trim().isEmpty();
                btnNext2.setEnabled(!isInputEmpty);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = usuari.getText().toString().trim();

                if (!userName.isEmpty()) {
                    // Guardar el nombre de usuario en SharedPreferences
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userName", userName);
                    editor.apply();

                    // Navegar a la pantalla de inicio
                    NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
                    NavController navController = navHostFragment.getNavController();
                    navController.navigate(R.id.navigation_home);
                } else {

                }

            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }
}
