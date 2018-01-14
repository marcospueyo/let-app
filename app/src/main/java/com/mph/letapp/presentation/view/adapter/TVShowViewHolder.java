package com.mph.letapp.presentation.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mph.letapp.R;
import com.mph.letapp.presentation.model.TVShowViewModel;
import com.mph.letapp.presentation.presenter.TVShowListPresenter;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TVShowViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.label_title) TextView tvTitle;

    @BindView(R.id.label_rating) TextView tvRating;

    @NonNull
    private Context mContext;

    @NonNull
    private TVShowListPresenter mPresenter;

    public TVShowViewHolder(Context context, View itemView, @NonNull TVShowListPresenter presenter) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = context;
        mPresenter = presenter;
    }

    public void render(final TVShowViewModel tvShowViewModel) {
        renderTitle(tvShowViewModel.title());
        renderRating(tvShowViewModel.rating());

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

    private String getFormattedRating(Double rating) {
        return String.format(Locale.getDefault(), "%.1f", rating) + " / 10";
    }
}
