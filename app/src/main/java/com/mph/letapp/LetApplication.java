package com.mph.letapp;

import android.support.multidex.MultiDexApplication;

import com.mph.letapp.di.application.ApplicationComponent;
import com.mph.letapp.di.ComponentFactory;

public class LetApplication extends MultiDexApplication {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = ComponentFactory.createApplicationComponent(this);
        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
