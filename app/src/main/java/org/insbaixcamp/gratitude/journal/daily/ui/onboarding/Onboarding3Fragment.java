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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import org.insbaixcamp.gratitude.journal.daily.R;
import org.insbaixcamp.gratitude.journal.daily.databinding.FragmentOnboarding3Binding;
import org.insbaixcamp.gratitude.journal.daily.databinding.FragmentOnboardingBinding;

public class Onboarding3Fragment extends Fragment {

    private FragmentOnboarding3Binding binding;
    private SharedPreferences sharedPreferences;


    public static Onboarding3Fragment newInstance() {
        return new Onboarding3Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOnboarding3Binding.inflate(inflater,container,false);
        View root = binding.getRoot();

        sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);


        Button btnNext2 = binding.btnOnboarding3;
        //TextInputLayout tfonboarding3 = binding.tfOnboarding3;

        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.navigation_home);
            }
        });
        return root;
        //return inflater.inflate(R.layout.fragment_onboarding3, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}