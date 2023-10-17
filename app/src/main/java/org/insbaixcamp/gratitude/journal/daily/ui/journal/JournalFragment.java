package org.insbaixcamp.gratitude.journal.daily.ui.journal;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.insbaixcamp.gratitude.journal.daily.R;

public class JournalFragment extends Fragment {

    private JournalViewModel mViewModel;

    public static JournalFragment newInstance() {
        return new JournalFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_journal, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(JournalViewModel.class);
        // TODO: Use the ViewModel
    }

}