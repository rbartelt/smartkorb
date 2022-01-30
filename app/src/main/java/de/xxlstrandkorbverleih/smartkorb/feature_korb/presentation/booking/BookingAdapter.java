package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.booking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.xxlstrandkorbverleih.smartkorb.R;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Booking;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingHolder>{

    private List<Booking> bookings = new ArrayList<>();

    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.booking_duration_item, parent, false);
        return new BookingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class BookingHolder extends RecyclerView.ViewHolder {

        private Date startDate;
        private Date endDate;

        public BookingHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
