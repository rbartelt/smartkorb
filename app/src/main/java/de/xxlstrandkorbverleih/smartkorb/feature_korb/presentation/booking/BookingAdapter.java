package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.booking;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

public class BookingAdapter {

    class BookingHolder extends RecyclerView.ViewHolder {

        private Date startDate;
        private Date endDate;

        public BookingHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
