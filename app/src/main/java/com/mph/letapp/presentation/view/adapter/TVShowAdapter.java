package com.mph.letapp.presentation.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mph.letapp.R;
import com.mph.letapp.presentation.model.TVShowViewModel;
import com.mph.letapp.presentation.presenter.TVShowListPresenter;

import java.util.ArrayList;
import java.util.List;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowViewHolder> {

    public static final int resID = R.layout.tvshow_row;

    @NonNull
    private Context mContext;

    @NonNull
    private final TVShowListPresenter mPresenter;

    private List<TVShowViewModel> mItemList;

    public TVShowAdapter(@NonNull Context context, @NonNull TVShowListPresenter presenter) {
        mContext = context;
        mPresenter = presenter;
        mItemList = new ArrayList<>();
    }

    @Override
    public TVShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TVShowViewHolder(mContext, getInflatedItemView(parent), mPresenter);
    }

    private View getInflatedItemView(ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return inflater.inflate(resID, viewGroup, false);
    }

    @Override
    public void onBindViewHolder(TVShowViewHolder holder, int position) {
        holder.render(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void setItemList(List<TVShowViewModel> models) {
        mItemList.clear();
        addToItemList(models);
    }

    public void addToItemList(List<TVShowViewModel> models) {
        mItemList.addAll(models);
        notifyDataSetChanged();
    }

    public void addItem(TVShowViewModel item) {
        mItemList.add(item);
        notifyItemInserted(mItemList.size() - 1);
    }

    public void updateItem(int position, TVShowViewModel item) {
        mItemList.set(position, item);
        notifyItemChanged(position, item);
    }

}
