package de.xxlstrandkorbverleih.smartkorb.feature_korb.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.data_source.KorbDao;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.data_source.KorbDatabase;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.model.Korb;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository.KorbRepository;

public class KorbRepositoryImplementation implements KorbRepository {

    private KorbDao korbDao;
    private LiveData<List<Korb>> allKörbe;

    @Inject
    public KorbRepositoryImplementation(Application application, KorbDao korbDao1) {
        korbDao = korbDao1;
        allKörbe = korbDao.getAllKörbe();                                   //create Livedata to observe?
    }

    public LiveData<List<Korb>> getKorbWithKeyUid(String keyUid) {
        return korbDao.getKorbWithKeyUid(keyUid);
    }

    @Override
    public LiveData<Korb> getBeachchairByUid(String beachchairUid) {
        return korbDao.getBeachchairByUid(beachchairUid);
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
        new DeleteAllKörbeAsyncTask(korbDao).execute();
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

