package de.xxlstrandkorbverleih.smartkorb.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.common.PermissionChecker;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.repository.KorbRepositoryImplementation;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.repository.LocationRepositoryFusedLocationProvider;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository.KorbRepository;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository.LocationRepository;

@InstallIn(SingletonComponent.class)
@Module
public class AppModule {

    @Singleton
    @Provides
    KorbRepository provideKorbRepository(Application application) {
        return new KorbRepositoryImplementation(application);
    }

    @Singleton
    @Provides
    LocationRepository provideLocationRepository(Application application) {
        return new LocationRepositoryFusedLocationProvider(application);
    }

    @Singleton
    @Provides
    PermissionChecker providePermissionChecker(Application application) {
        return new PermissionChecker(application);
    }
}
