package org.insbaixcamp.gratitude.journal.daily.ui.home;

import android.content.Context;
import android.util.Log;
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
    private OnItemClickListener onItemClickListener; // Agregado

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

        // Agregado: Configura el clic en el elemento
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    // Agregado: Define la interfaz OnItemClickListener
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Agregado: Método para establecer el listener desde fuera del adaptador
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
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
            if (entry.getFeeling() == 1) {
                iconImageView.setImageResource(R.drawable.llorando);
            } else if (entry.getFeeling() == 2) {
                iconImageView.setImageResource(R.drawable.triste);
            } else if (entry.getFeeling() == 3) {
                iconImageView.setImageResource(R.drawable.indiferente);
            } else if (entry.getFeeling() == 4) {
                iconImageView.setImageResource(R.drawable.contento);
            } else if (entry.getFeeling() == 5) {
                iconImageView.setImageResource(R.drawable.muy_contento);
            }
        }
    }
}

