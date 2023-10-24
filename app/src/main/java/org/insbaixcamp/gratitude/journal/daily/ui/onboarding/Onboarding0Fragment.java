package org.insbaixcamp.gratitude.journal.daily.ui.onboarding;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import org.insbaixcamp.gratitude.journal.daily.R;

public class Onboarding0Fragment extends Fragment {

    public static Onboarding0Fragment newInstance() {
        return new Onboarding0Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding0, container, false);

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
        // Realizar la navegación a OnboardingFragment
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.navigation_onboarding);
    }
}