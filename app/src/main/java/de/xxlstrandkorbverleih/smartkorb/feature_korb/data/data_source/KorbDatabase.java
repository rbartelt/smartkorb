package de.xxlstrandkorbverleih.smartkorb.feature_korb.data.data_source;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;

@Database(entities = {Korb.class}, version = 3)
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

    //For testing we fill the Database with som entities on their creation
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
