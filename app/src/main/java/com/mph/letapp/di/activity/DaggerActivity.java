package com.mph.letapp.di.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mph.letapp.LetApplication;
import com.mph.letapp.di.ComponentFactory;


public abstract class DaggerActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(getActivityComponent());
    }

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = ComponentFactory.createActivityComponent(this,
                    getXaccApplication());
        }
        return activityComponent;
    }

    private LetApplication getXaccApplication() {
        return ((LetApplication) getApplication());
    }

    protected abstract void inject(final ActivityComponent activityComponent);
}
