package de.xxlstrandkorbverleih.smartkorb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "korb_table")
public class Korb {

    @PrimaryKey(autoGenerate = true)
    private int id;                     //primary key db

    private int number;                 //number of korb should be unique
    private String type;                //which type normal, xl or xxl
    private double latitude;            //latitude location
    private double longitude;           //longitude location
    private double accuracy;            //accuracy of location

    public Korb(int number, String type, double latitude, double longitude, double accuracy) {
        this.number = number;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAccuracy() {
        return accuracy;
    }
}
