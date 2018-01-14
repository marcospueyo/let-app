package com.mph.letapp.di;


import com.mph.letapp.LetApplication;
import com.mph.letapp.di.activity.ActivityComponent;
import com.mph.letapp.di.activity.DaggerActivity;
import com.mph.letapp.di.application.ApplicationComponent;


public final class ComponentFactory {

    public ComponentFactory() {
    }

    public static ApplicationComponent createApplicationComponent(
            final LetApplication letApplication) {
        return ApplicationComponent.Initializer.init(letApplication);
    }

    public static ActivityComponent createActivityComponent(final DaggerActivity daggerActivity,
                                                            final LetApplication letApplication) {
        return ActivityComponent.Initializer.init(daggerActivity,
                letApplication.getApplicationComponent());
    }
}
