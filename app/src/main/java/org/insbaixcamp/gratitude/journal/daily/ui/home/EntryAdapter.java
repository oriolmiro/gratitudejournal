package org.insbaixcamp.gratitude.journal.daily.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.insbaixcamp.gratitude.journal.daily.R;
import org.insbaixcamp.gratitude.journal.daily.model.JournalEntry;

import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {
    private List<JournalEntry> entries;
    private Context context;

    public EntryAdapter(List<JournalEntry> entries, Context context) {
        this.entries = entries;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JournalEntry entry = entries.get(position);
        holder.bind(entry);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dateTextView;
        private TextView titleTextView;
        private TextView contentTextView;
        private ImageView iconImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.tv_date);
            titleTextView = itemView.findViewById(R.id.tv_title_day);
            contentTextView = itemView.findViewById(R.id.tv_content_entry_day);
            iconImageView = itemView.findViewById(R.id.iv_icon);
        }

        public void bind(JournalEntry entry) {
            dateTextView.setText(entry.getDate());
            titleTextView.setText(entry.getTitleSun());
            contentTextView.setText(entry.getMessageSun());
            iconImageView.setImageResource(entry.getFeeling());
        }
    }
}
