package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.showKoerbe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.xxlstrandkorbverleih.smartkorb.R;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;

//TODO: create Marker for korb Nfc Uid and key NFC UID is set
public class KorbAdapter extends RecyclerView.Adapter<KorbAdapter.KorbHolder> {
    private List<Korb> koerbe = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public KorbHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.korb_item, parent, false);
        return new KorbHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KorbHolder holder, int position) {
        Korb currentKorb = koerbe.get(position);
        holder.textViewType.setText(currentKorb.getType());
        holder.textViewNumber.setText(String.valueOf(currentKorb.getNumber()));
        holder.textViewLocation.setText(String.valueOf(currentKorb.getAccuracy())); //TODO: set updatetime
        holder.checkBoxKorbUid.setChecked(currentKorb.isKorbUidSet());
        holder.checkBoxKeyUid.setChecked(currentKorb.isKeyUidSet());
    }

    @Override
    public int getItemCount() {
        return koerbe.size();
    }

    //insert körbe in ResyclerView
    public void setKoerbe(List<Korb> koerbe) {
        this.koerbe = koerbe;
        notifyDataSetChanged();
    }

    public Korb getKorbAt(int position) {
        return koerbe.get(position);
    }

    class KorbHolder extends RecyclerView.ViewHolder {
        private TextView textViewType;
        private TextView textViewNumber;
        private TextView textViewLocation;
        private CheckBox checkBoxKeyUid;
        private CheckBox checkBoxKorbUid;

        public KorbHolder(View itemView) {
            super(itemView);
            textViewType = itemView.findViewById(R.id.text_view_type);
            textViewNumber = itemView.findViewById(R.id.text_view_number);
            textViewLocation = itemView.findViewById(R.id.text_view_location_set_on);
            checkBoxKeyUid = itemView.findViewById(R.id.check_box_key_uid);
            checkBoxKorbUid = itemView.findViewById(R.id.check_box_korb_uid);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(koerbe.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Korb korb);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}