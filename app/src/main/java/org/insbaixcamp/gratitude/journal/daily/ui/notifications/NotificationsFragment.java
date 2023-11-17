package org.insbaixcamp.gratitude.journal.daily.ui.notifications;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.insbaixcamp.gratitude.journal.daily.R;
import org.insbaixcamp.gratitude.journal.daily.databinding.FragmentNotificationsBinding;
import org.insbaixcamp.gratitude.journal.daily.tools.SettingsManager;

public class NotificationsFragment extends Fragment implements View.OnClickListener {

    private FragmentNotificationsBinding binding;
    private TextView tvSaludo;
    private TextView tvNombreUsuario;

    private TextView tvEmailUsuario;
    private TextInputEditText etNuevoNombreUsuario;
    private TextInputLayout tiNuevoNombreUsuario;
    private Button btnActualizarNombreUsuario;

    private Button btnPoliticasPrivacidad;

    private SettingsManager settingsManager;

    private TextInputEditText etNuevoEmailUsuario;
    private TextInputLayout tilNuevoEmailUsuario;
    private Button btnActualizarEmailUsuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tvSaludo = root.findViewById(R.id.tvSaludo);
        tvEmailUsuario = root.findViewById(R.id.tvEmailUsuario);
        btnPoliticasPrivacidad = root.findViewById(R.id.btnPoliticasPrivacidad);
        tvNombreUsuario = root.findViewById(R.id.tvNombreUsuario);
        etNuevoNombreUsuario = root.findViewById(R.id.etNuevoNombreUsuario);
        tiNuevoNombreUsuario = root.findViewById(R.id.tiNuevoNombreUsuario);
        btnActualizarNombreUsuario = root.findViewById(R.id.btnActualizarNombreUsuario);
        etNuevoEmailUsuario = root.findViewById(R.id.etEmailUsuario);
        tilNuevoEmailUsuario = root.findViewById(R.id.tilEmailUsuario);
        btnActualizarEmailUsuario = root.findViewById(R.id.btnConfirmarEmail);

        settingsManager = new SettingsManager(getContext());

        mostrarSaludoYNombreUsuario();
        btnActualizarNombreUsuario.setOnClickListener(this);
        btnActualizarEmailUsuario.setOnClickListener(this);
        btnPoliticasPrivacidad.setOnClickListener(this);




        //final TextView textView = binding.textNotifications;
        //notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void mostrarSaludoYNombreUsuario() {
        String nombreUsuario = settingsManager.getUserName();
        String emailUsuario = settingsManager.getUserEmail();
        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
            tvSaludo.setText("HOLA");
            tvNombreUsuario.setText(nombreUsuario);
            tvEmailUsuario.setText(emailUsuario);

        } else {
            tvSaludo.setText("HOLA");
            tvNombreUsuario.setText("Nombre de Usuario");
        }
        // Limpia el error en TextInputLayout
        tiNuevoNombreUsuario.setError(null);
    }

    private void politicaPrivacidad(String url) {
        // Crear un Intent con la acción ACTION_VIEW y la URI de la página web
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        // Verificar si hay alguna aplicación que pueda manejar la acción
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Iniciar la actividad para mostrar la página web
            startActivity(intent);
        } else {
            // Si no se encuentra ninguna aplicación para manejar la acción, abrir en el navegador web predeterminado
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(webIntent);
        }
    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.btnActualizarNombreUsuario) {
            actualizarNombreUsuario();
        } else if (viewId == R.id.btnPoliticasPrivacidad) {
            politicaPrivacidad("https://rodriappsdev.blogspot.com/2023/11/html-charsetutf-8-nameviewport.html");
        } else if (viewId == R.id.btnConfirmarEmail) {
            actualizarEmailUsuario();
        }

    }

    private void actualizarEmailUsuario() {
        if (etNuevoEmailUsuario != null) {
            String nuevoEmailUsuario = etNuevoEmailUsuario.getText().toString();
            if (!nuevoEmailUsuario.isEmpty()) {
                settingsManager.setUserEmail(nuevoEmailUsuario);
                // Puedes agregar más lógica aquí según tus necesidades

                mostrarSaludoYNombreUsuario();
            } else {
                tilNuevoEmailUsuario.setError("El correo electrónico no puede estar vacío.");
            }
        } else {
            // Manejo del caso en que etNuevoEmailUsuario sea nulo
        }
    }


    private void actualizarNombreUsuario() {
        String nuevoNombreUsuario = etNuevoNombreUsuario.getText().toString();
        if (!nuevoNombreUsuario.isEmpty()) {
            settingsManager.setUserName(nuevoNombreUsuario);
            mostrarSaludoYNombreUsuario();
        } else {
            tiNuevoNombreUsuario.setError("El nombre de usuario no puede estar vacío.");
        }
    }
}