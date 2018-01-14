package com.mph.letapp.presentation.navigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

public class RouterImpl implements Router {

    @NonNull
    private Context mContext;

    public RouterImpl(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public void openBrowser(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        mContext.startActivity(intent);
    }
}
