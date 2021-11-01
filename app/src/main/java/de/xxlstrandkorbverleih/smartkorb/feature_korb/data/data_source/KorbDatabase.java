package de.xxlstrandkorbverleih.smartkorb.feature_korb.data.data_source;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;

@Database(entities = {Korb.class}, version = 1)
public abstract class KorbDatabase extends RoomDatabase {

    private static KorbDatabase instance;

    public abstract  KorbDao korbDao();

    public static synchronized KorbDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    KorbDatabase.class, "korb_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    //For testing we fill the Database with som entities on their creation
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    //Room will not allow Database operations in Main Thread
    //We have to create the Database operation in async Tasks
    private static class PopulateDbAsyncTask extends AsyncTask<Void ,Void, Void> {
        private KorbDao korbDao;

        private PopulateDbAsyncTask(KorbDatabase db) {
            korbDao = db.korbDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            korbDao.insert(new Korb(1,"XXL",123.000,123.123,2.7));
            korbDao.insert(new Korb(2,"XL",124.000,124.123,2.7));
            korbDao.insert(new Korb(3,"Normal",125.000,125.123,2.7));
            return null;
        }
    }
}
