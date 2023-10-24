package org.insbaixcamp.gratitude.journal.daily.ui.onboarding;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.insbaixcamp.gratitude.journal.daily.R;
import org.insbaixcamp.gratitude.journal.daily.databinding.FragmentOnboarding2Binding;
import org.insbaixcamp.gratitude.journal.daily.databinding.FragmentOnboardingBinding;

public class Onboarding2Fragment extends Fragment {

    private FragmentOnboarding2Binding binding;

    public static Onboarding2Fragment newInstance() {
        return new Onboarding2Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOnboarding2Binding.inflate(inflater,container,false);
        View root = binding.getRoot();

        Button btnNext = binding.btnOnboarding2Next;
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.navigation_onboarding3);

            }
        });
        return root;

        //return inflater.inflate(R.layout.fragment_onboarding2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}