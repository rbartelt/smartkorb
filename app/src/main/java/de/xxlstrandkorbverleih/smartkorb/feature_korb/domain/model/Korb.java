package de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model;

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
    private String keyUid;              //UID of Key NFC Tag
    private String korbUid;              //UID of Korb NFC Tag

    public Korb(int number, String type, double latitude, double longitude, double accuracy, String keyUid, String korbUid) {
        this.number = number;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
        this.keyUid = keyUid;
        this.korbUid = korbUid;
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

    public String getKeyUid() {
        return keyUid;
    }

    public void setKeyUid(String keyUid) {
        this.keyUid = keyUid;
    }

    public String getKorbUid() {
        return korbUid;
    }

    public void setKorbUid(String korbUid) {
        this.korbUid = korbUid;
    }

}
