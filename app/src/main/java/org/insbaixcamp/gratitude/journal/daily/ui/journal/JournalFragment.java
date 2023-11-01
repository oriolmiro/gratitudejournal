package org.insbaixcamp.gratitude.journal.daily.ui.journal;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.insbaixcamp.gratitude.journal.daily.R;
import org.insbaixcamp.gratitude.journal.daily.databinding.FragmentHomeBinding;
import org.insbaixcamp.gratitude.journal.daily.databinding.FragmentJournalBinding;

import java.util.List;

public class JournalFragment extends Fragment {

    private JournalViewModel mViewModel;
    private FragmentJournalBinding binding;

    public static JournalFragment newInstance() {
        return new JournalFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentJournalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
        for(Fragment fragment : fragments){
            Log.d("DEBUG_TAG", "Fragment: " + fragment.getClass().getSimpleName());
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(JournalViewModel.class);
        // TODO: Use the ViewModel
    }

}