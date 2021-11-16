package de.xxlstrandkorbverleih.smartkorb.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.scopes.ActivityRetainedScoped;
import dagger.hilt.android.scopes.ViewModelScoped;
import dagger.hilt.components.SingletonComponent;
import de.xxlstrandkorbverleih.smartkorb.SmartkorbApp;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.repository.KorbRepositoryImplementation;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository.KorbRepository;

@InstallIn(SingletonComponent.class)
@Module
public class AppModule {

    @Singleton
    @Provides
    KorbRepository provideKorbRepository(Application application) {
        return new KorbRepositoryImplementation(application);
    }
}
