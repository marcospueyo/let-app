package com.mph.letapp.di.activity;

import com.mph.letapp.di.application.ApplicationComponent;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                ActivityModule.class
        }
)
public interface ActivityComponent extends ActivityComponentInjects, ActivityComponentExposes  {


    final class Initializer {

        static public ActivityComponent init(final DaggerActivity daggerActivity,
                                             final ApplicationComponent applicationComponent) {
                        return DaggerActivityComponent.builder()
                    .applicationComponent(applicationComponent)
                    .activityModule(new ActivityModule(daggerActivity))
                    .build();
        }

        private Initializer() {
        }
    }
}
