package com.troya.menuplanner.controllers.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.Window;
import android.widget.EditText;

import com.troya.menuplanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SetNameDialog extends Dialog {

    private static final int LAYOUT = R.layout.dialog_add_item;

    private OnPositiveListener mOnPositiveListener;
    private String mName;
    @StringRes
    private int mHint;

    public SetNameDialog(@NonNull Context context, String name, @StringRes int hint, OnPositiveListener onPositiveListener) {
        super(context);
        mOnPositiveListener = onPositiveListener;
        mName = name;
        mHint = hint;
    }

    private boolean mIsPassed = false;

    @BindView(R.id.tilName)
    TextInputLayout mNameLayout;

    @BindView(R.id.editName)
    EditText mNameView;

    public interface OnPositiveListener {
        String onPositive(String text);
    }

    public boolean isPassed() {
        return mIsPassed;
    }

    public String getText() {
     return mNameView.getText().toString();
    }

    /*------------------------------- Lifecycle -------------------------------*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        setContentView(LAYOUT);

        ButterKnife.bind(this);

        mNameView.setHint(mHint);

        if (!TextUtils.isEmpty(mName)) {
            mNameView.setText(mName);
        }
    }


    /*------------------------------- Callbacks -------------------------------*/

    @OnTextChanged(R.id.editName)
    void onNameTextChanged() {
        mNameLayout.setError(null);
    }

    @OnClick(R.id.btn_ok)
    void onOkClick() {
        mNameLayout.setError(null);

        String error = mOnPositiveListener.onPositive(getText());
        if (TextUtils.isEmpty(error)) {
            mIsPassed = true;
            dismiss();
        }
        else {
            mNameLayout.setError(error);
        }
    }

    @OnClick(R.id.btn_cancel)
    void onCancelClick() {
        cancel();
    }
}
