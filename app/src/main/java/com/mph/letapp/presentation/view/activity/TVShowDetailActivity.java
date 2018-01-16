package com.mph.letapp.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.mph.letapp.R;
import com.mph.letapp.di.activity.ActivityComponent;
import com.mph.letapp.di.activity.DaggerActivity;
import com.mph.letapp.presentation.TVShowDetailView;
import com.mph.letapp.presentation.model.TVShowViewModel;
import com.mph.letapp.presentation.presenter.TVShowDetailPresenter;
import com.mph.letapp.presentation.view.component.SwipeDetector;
import com.mph.letapp.presentation.view.component.TouchableLinearLayout;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class TVShowDetailActivity extends DaggerActivity implements TVShowDetailView,
        SwipeDetector.SwipeListener {

    public static final String SHOW_ID_KEY = "show_id";

    @SuppressWarnings("unused")
    public static final String TAG = TVShowDetailActivity.class.getSimpleName();

    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    @BindView(R.id.iv_logo) ImageView ivLogo;

    @BindView(R.id.tv_title) TextView tvTitle;

    @BindView(R.id.tv_description) TextView tvDescription;

    @BindView(R.id.tv_rating) TextView tvRating;

    @BindView(R.id.ll_swipe_area)
    TouchableLinearLayout llSwipeArea;

    @BindView(R.id.ib_left) ImageButton ibLeft;

    @BindView(R.id.ib_right) ImageButton ibRight;

    @Inject
    TVShowDetailPresenter mPresenter;

    private String mShowID;

    private RequestManager mRequestManager;

    public static Intent getNewIntent(Context context, String tvShowID) {
        Intent intent = new Intent(context, TVShowDetailActivity.class);
        intent.putExtra(SHOW_ID_KEY, tvShowID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_detail);
        ButterKnife.bind(this);
        loadShowDetails();
        initializePresenter();
        initializeRequestManager();
        initializeSwipingListeners();
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void showTVShow(TVShowViewModel tvShow) {
        setTitle(tvShow.title());
        renderLogo(tvShow.image());
        renderTitle(tvShow.title());
        renderDescription(tvShow.description());
        renderRating(tvShow.rating());
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoadError() {
        Snackbar.make(findViewById(android.R.id.content),
                getString(R.string.entity_detail_load_error), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSwipeLeft() {
        mPresenter.onSwipeLeft();
    }

    @Override
    public void onSwipeRight() {
        mPresenter.onSwipeRight();
    }

    private void loadShowDetails() {
        if (getIntent().hasExtra(SHOW_ID_KEY)) {
            mShowID = getIntent().getStringExtra(SHOW_ID_KEY);
        }
    }

    private void initializePresenter() {
        if (mShowID != null && mPresenter != null) {
            mPresenter.initialize(mShowID);
        }
    }

    private void initializeRequestManager() {
        mRequestManager = Glide.with(this);
    }

    private void initializeSwipingListeners() {
        ibLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onSwipeLeft();
            }
        });
        ibRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onSwipeRight();
            }
        });
        llSwipeArea.setOnTouchListener(new SwipeDetector(100, this));
    }

    private void renderLogo(String image) {
        RequestOptions requestOptions = new RequestOptions()
                .error(ContextCompat.getDrawable(this, R.drawable.ic_tv_placeholder));
        mRequestManager
                .load(image)
                .apply(requestOptions)
                .transition(withCrossFade())
                .into(ivLogo);
    }

    private void renderTitle(String title) {
        tvTitle.setText(title);
    }

    private void renderRating(Double rating) {
        tvRating.setText(getFormattedRating(rating));
    }

    private void renderDescription(String description) {
        tvDescription.setText(description);
    }

    private String getFormattedRating(Double rating) {
        return String.format(Locale.getDefault(), "%.1f", rating) + " / 10";
    }
}
