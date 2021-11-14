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
import de.xxlstrandkorbverleih.smartkorb.feature_korb.data.repository.KorbRepositoryImplementation;
import de.xxlstrandkorbverleih.smartkorb.feature_korb.domain.repository.KorbRepository;

@Module
@InstallIn(ActivityComponent.class)
public class AppModule {

    @Provides
    @ActivityRetainedScoped
    KorbRepository provideKorbRepository(Application application) {
        return new KorbRepositoryImplementation(application);
    }
}
