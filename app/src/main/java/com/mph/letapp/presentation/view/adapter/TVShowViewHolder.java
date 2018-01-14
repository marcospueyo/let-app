package com.mph.letapp.presentation.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import com.mph.letapp.R;
import com.mph.letapp.presentation.model.TVShowViewModel;
import com.mph.letapp.presentation.presenter.TVShowListPresenter;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

class TVShowViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.label_title) TextView tvTitle;

    @BindView(R.id.label_rating) TextView tvRating;

    @BindView(R.id.iv_logo) ImageView ivLogo;

    @NonNull
    private Context mContext;

    @NonNull
    private TVShowListPresenter mPresenter;

    @NonNull
    private final RequestManager mRequestManager;

    TVShowViewHolder(@NonNull Context context, View itemView,
                     @NonNull TVShowListPresenter presenter,
                     @NonNull RequestManager requestManager) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
        mPresenter = presenter;
        mRequestManager = requestManager;
    }

    void render(final TVShowViewModel tvShowViewModel) {
        renderTitle(tvShowViewModel.title());
        renderRating(tvShowViewModel.rating());
        renderImage(tvShowViewModel.image());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onTVShowSelected(tvShowViewModel);
            }
        });
    }

    private void renderTitle(String title) {
        tvTitle.setText(title);
    }

    private void renderRating(Double rating) {
        tvRating.setText(getFormattedRating(rating));
    }

    private void renderImage(String image) {
        RequestOptions requestOptions = new RequestOptions()
                .error(ContextCompat.getDrawable(mContext, R.drawable.ic_tv_placeholder));
        mRequestManager
                .load(image)
                .apply(requestOptions)
                .transition(withCrossFade())
                .into(ivLogo);
    }

    private String getFormattedRating(Double rating) {
        return String.format(Locale.getDefault(), "%.1f", rating) + " / 10";
    }
}
