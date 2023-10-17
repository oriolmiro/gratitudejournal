package org.insbaixcamp.gratitude.journal.daily.ui.insight;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.insbaixcamp.gratitude.journal.daily.R;

public class InsightFragment extends Fragment {

    private InsightViewModel mViewModel;

    public static InsightFragment newInstance() {
        return new InsightFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_insight, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InsightViewModel.class);
        // TODO: Use the ViewModel
    }

}