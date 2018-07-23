package com.troya.menuplanner.helpers;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.troya.menuplanner.R;


public class AppSnackbar {

    public static Snackbar make(@NonNull View view, @NonNull CharSequence text,
                                 int duration){
        Snackbar snackbar = Snackbar.make(view, text, duration);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundResource(R.color.colorSecondary);
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        return snackbar;
    }

    public static Snackbar make(@NonNull View view, @StringRes int resId,
                                int duration){
        return make(view, view.getResources().getText(resId), duration);
    }
}
