package de.xxlstrandkorbverleih.smartkorb.di;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ActivityContext;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.common.PermissionChecker;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.data_source.KorbDao;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.data_source.KorbDatabase;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.repository.KorbRepositoryFirebaseImplementation;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.repository.KorbRepositoryImplementation;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.repository.LocationRepositoryFusedLocationProvider;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.repository.LocationRepositoryLocationManager;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository.KorbRepository;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository.LocationRepository;

@InstallIn(SingletonComponent.class)
@Module
public class AppModule {

    /*
    @Singleton
    @Provides
    KorbRepository provideKorbRepository(Application application, KorbDao korbDao) {
        return new KorbRepositoryImplementation(application, korbDao);
    }
*/
    @Singleton
    @Provides
    KorbRepository provideKorbRepository() {
        return new KorbRepositoryFirebaseImplementation();
    }
    /*
        @Singleton
        @Provides
        LocationRepository provideLocationRepository(Application application) {
            return new LocationRepositoryFusedLocationProvider(application);
        }*/

    @Singleton
    @Provides
    LocationRepository provideLocationRepository(@ApplicationContext Context context) {
        return new LocationRepositoryLocationManager(context);
    }

    @Singleton
    @Provides
    PermissionChecker providePermissionChecker(Application application) {
        return new PermissionChecker(application);
    }

    @Singleton
    @Provides
    KorbDatabase provideKorbDatabase(@ApplicationContext Context context) {
      return Room.databaseBuilder(context.getApplicationContext(), KorbDatabase.class, "korb_database").fallbackToDestructiveMigration().build();
    }

    @Singleton
    @Provides
    KorbDao provideKorbDao(KorbDatabase db) {
        return db.korbDao();
    }
}

