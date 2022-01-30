package de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model;

import java.util.Date;

public class Booking {

    private Date start;
    private Date end;
    private Korb korb;

    public Booking(Date start, Date end, Korb korb) {
        this.start = start;
        this.end = end;
        this.korb = korb;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public Korb getKorb() {
        return korb;
    }
}
