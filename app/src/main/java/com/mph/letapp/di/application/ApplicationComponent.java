package com.mph.letapp.di.application;


import com.mph.letapp.LetApplication;
import com.mph.letapp.di.application.module.DataModule;
import com.mph.letapp.di.application.module.InteractorModule;
import com.mph.letapp.di.application.module.MappersModule;
import com.mph.letapp.di.application.module.NetworkModule;
import com.mph.letapp.di.application.module.RepositoryModule;
import com.mph.letapp.di.application.module.ServiceModule;
import com.mph.letapp.di.application.module.ThreadingModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        MappersModule.class,
        NetworkModule.class,
        ServiceModule.class,
        RepositoryModule.class,
        InteractorModule.class,
        DataModule.class,
        ThreadingModule.class
})
public interface ApplicationComponent extends ApplicationComponentInjects,
        ApplicationComponentExposes {

    final class Initializer {

        static public ApplicationComponent init(final LetApplication letApplication) {
            return DaggerApplicationComponent
                    .builder()
                    .applicationModule(new ApplicationModule(letApplication))
                    .mappersModule(new MappersModule())
                    .networkModule(new NetworkModule())
                    .serviceModule(new ServiceModule())
                    .repositoryModule(new RepositoryModule())
                    .interactorModule(new InteractorModule())
                    .dataModule(new DataModule())
                    .threadingModule(new ThreadingModule())
                    .build();
        }

        private Initializer() {

        }
    }
}
