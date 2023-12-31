package org.insbaixcamp.gratitude.journal.daily.ui.onboarding;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.insbaixcamp.gratitude.journal.daily.MainActivity;
import org.insbaixcamp.gratitude.journal.daily.R;
import org.insbaixcamp.gratitude.journal.daily.tools.SettingsManager;

public class Onboarding0Fragment extends Fragment {

    public static Onboarding0Fragment newInstance() {
        return new Onboarding0Fragment();
    }

    SettingsManager settingsManager;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding0, container, false);

        settingsManager = new SettingsManager(getContext()); // Suponiendo que tienes una instancia de SettingsManager
        settingsManager.userAuth();
        //Esconder BottonNavigationBar
        //((MainActivity)getContext()).binding.navView.setVisibility(View.INVISIBLE); // O View.VISIBLE para mostrarlo nuevamente

        // Crear un Handler y postergar la navegación
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToOnboardingFragment();
            }
        }, 3000); // Retraso de 3 segundos (3000 milisegundos)

        return view;
    }

    private void navigateToOnboardingFragment() {

        int openCount = settingsManager.addCount();
        Log.i("ASDF",settingsManager.gatherDeviceInfo().toString());

        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        NavController navController = navHostFragment.getNavController();

      if (!settingsManager.isOnboardingFinished()) {
            navController.navigate(R.id.navigation_onboarding);
        }
      else{
          ((MainActivity)getContext()).binding.navView.setVisibility(View.VISIBLE); // O View.VISIBLE para mostrarlo nuevamente
          navController.navigate(R.id.navigation_home);

      }
    }
}
