package com.troya.menuplanner.Temp.Main.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

public abstract class BaseTabFragment extends Fragment {

    private Context mContext;
    private String mTitle;
    protected View mView;

    protected void setTitle(String title) {
        this.mTitle = title;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    @Override
    public Context getContext() {
        return mContext;
    }
}
