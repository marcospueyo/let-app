package com.mph.letapp.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mph.letapp.R;
import com.mph.letapp.di.activity.ActivityComponent;
import com.mph.letapp.di.activity.DaggerActivity;

public class TVShowDetailActivity extends DaggerActivity {

    public static final String SHOW_ID_KEY = "show_id";

    public static Intent getNewIntent(Context context, String tvShowID) {
        Intent intent = new Intent(context, TVShowDetailActivity.class);
        intent.putExtra(SHOW_ID_KEY, tvShowID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_detail);
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {

    }
}
