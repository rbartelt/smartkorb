package de.xxlstrandkorbverleih.smartkorb;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class KorbRepository {

    private KorbDao korbDao;
    private LiveData<List<Korb>> allKörbe;

    public KorbRepository(Application application) {
        KorbDatabase database = KorbDatabase.getInstance(application);      //get Database
        korbDao = database.korbDao();                                       //create DAO
        allKörbe = korbDao.getAllKörbe();                                   //create Livedata to observe?
    }

    public void insert(Korb korb) {
        new InsertKorbAsyncTask(korbDao).execute(korb);

    }

    public void update(Korb korb) {
        new UpdateKorbAsyncTask(korbDao).execute(korb);

    }

    public void delete(Korb korb) {
        new DeleteKorbAsyncTask(korbDao).execute(korb);

    }

    public void deletAllKörbe() {
        new DeleteKorbAsyncTask(korbDao).execute();
    }

    public LiveData<List<Korb>> getAllKörbe() {
        return allKörbe;
    }

    //Room will not allow Database operations in Main Thread
    //We have to create the Database operation in async Tasks
    private static class InsertKorbAsyncTask extends AsyncTask<Korb, Void, Void> {
        private KorbDao korbDao;

        //Since the class is static we have to pass the korbDao via constructor
        private InsertKorbAsyncTask(KorbDao korbDao) {
            this.korbDao = korbDao;
        }

        @Override
        protected Void doInBackground(Korb... körbe) {
            korbDao.insert(körbe[0]);
            return null;
        }
    }

    private static class DeleteKorbAsyncTask extends AsyncTask<Korb, Void, Void> {
        private KorbDao korbDao;

        //Since the class is static we have to pass the korbDao via constructor
        private DeleteKorbAsyncTask(KorbDao korbDao) {
            this.korbDao = korbDao;
        }

        @Override
        protected Void doInBackground(Korb... körbe) {
            korbDao.delete(körbe[0]);
            return null;
        }
    }

    private static class UpdateKorbAsyncTask extends AsyncTask<Korb, Void, Void> {
        private KorbDao korbDao;

        //Since the class is static we have to pass the korbDao via constructor
        private UpdateKorbAsyncTask(KorbDao korbDao) {
            this.korbDao = korbDao;
        }

        @Override
        protected Void doInBackground(Korb... körbe) {
            korbDao.update(körbe[0]);
            return null;
        }
    }

    private static class DeleteAllKörbeAsyncTask extends AsyncTask<Void, Void, Void> {
        private KorbDao korbDao;

        //Since the class is static we have to pass the korbDao via constructor
        private DeleteAllKörbeAsyncTask(KorbDao korbDao) {
            this.korbDao = korbDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            korbDao.deleteAllKörbe();
            return null;
        }
    }
}

