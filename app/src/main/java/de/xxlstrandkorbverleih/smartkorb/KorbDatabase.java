package de.xxlstrandkorbverleih.smartkorb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Korb.class}, version = 1)
public abstract class KorbDatabase extends RoomDatabase {

    private static KorbDatabase instance;

    public abstract  KorbDao korbDao();

    public static synchronized KorbDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    KorbDatabase.class, "korb_database").fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
