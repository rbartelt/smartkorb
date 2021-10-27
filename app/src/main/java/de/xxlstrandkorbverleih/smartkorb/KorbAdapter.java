package de.xxlstrandkorbverleih.smartkorb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class KorbAdapter extends RecyclerView.Adapter<KorbAdapter.KorbHolder> {
    private List<Korb> körbe = new ArrayList<>();

    @NonNull
    @Override
    public KorbHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.korb_item, parent, false);
        return new KorbHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KorbHolder holder, int position) {
        Korb currentKorb = körbe.get(position);
        holder.textViewType.setText(currentKorb.getType());
        holder.textViewNumber.setText(String.valueOf(currentKorb.getNumber()));
        holder.textViewLocation.setText(String.valueOf(currentKorb.getAccuracy())); //TODO: set updatetime
    }

    @Override
    public int getItemCount() {
        return körbe.size();
    }

    //insert körbe in ResyclerView
    public void setKörbe(List<Korb> körbe) {
        this.körbe = körbe;
        notifyDataSetChanged();
    }

    public Korb getKorbAt(int position) {
        return körbe.get(position);
    }

    class KorbHolder extends RecyclerView.ViewHolder {
        private TextView textViewType;
        private TextView textViewNumber;
        private TextView textViewLocation;

        public KorbHolder(View itemView) {
            super(itemView);
            textViewType = itemView.findViewById(R.id.text_view_type);
            textViewNumber = itemView.findViewById(R.id.text_view_number);
            textViewLocation = itemView.findViewById(R.id.text_view_location_set_on);
        }
    }
}