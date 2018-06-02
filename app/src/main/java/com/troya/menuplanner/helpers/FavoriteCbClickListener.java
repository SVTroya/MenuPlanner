package com.troya.menuplanner.helpers;

import android.content.Context;
import android.view.View;

public class FavoriteCbClickListener implements View.OnClickListener {

    private Long mId;

    public FavoriteCbClickListener(Context context, long id) {
        this.mId = id;
    }

    @Override
    public void onClick(View checkbox) {

    }
}
