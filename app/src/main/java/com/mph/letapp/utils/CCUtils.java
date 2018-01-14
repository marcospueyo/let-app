package com.mph.letapp.utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Date;


public class CCUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        fragmentManager.beginTransaction()
                .replace(frameId, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public static String getFormattedLastActivity(Date date) {
        return DateUtils.dateToString(date,
                DateUtils.isToday(date) ? DateUtils.SHORT_TIME_FORMAT : DateUtils.DATE_FORMAT);
    }

    public static String getFormattedMessageDate(Date date) {
        return DateUtils.dateToString(date,
                DateUtils.isToday(date) ? DateUtils.SHORT_TIME_FORMAT : DateUtils.FULL_DATE_FORMAT);
    }

    public static boolean isAPI_L_OrAbove() {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static RecyclerView.AdapterDataObserver getScrolldownObserver(
            final RecyclerView.Adapter adapter,
            final LinearLayoutManager layoutManager,
            final RecyclerView recyclerView) {
        return new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int messageCount = adapter.getItemCount();
                int lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();

                if (lastVisiblePosition == -1 || (positionStart >= (messageCount - 1)
                        && lastVisiblePosition == (positionStart - 1))) {
                    recyclerView.smoothScrollToPosition(positionStart);
                }
            }

            @Override
            public void onChanged() {
                super.onChanged();
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        };
    }

    public static View.OnLayoutChangeListener getScrolldownLayoutObserver(
            final RecyclerView.Adapter adapter,
            final RecyclerView recyclerView) {
        return new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft,
                                       int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom)
                    recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        };
    }
}
