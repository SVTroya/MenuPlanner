package com.troya.menuplanner.controllers.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Window;
import android.widget.EditText;

import com.troya.menuplanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddRecipeDialog extends Dialog {

    private OnPositiveListener mOnPositiveListener;

    public AddRecipeDialog(@NonNull Context context, OnPositiveListener onPositiveListener) {
        super(context);
        mOnPositiveListener = onPositiveListener;
    }
    private static final int LAYOUT = R.layout.dialog_add_item;

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
    };

    /*------------------------------- Lifecycle -------------------------------*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        setContentView(LAYOUT);

        ButterKnife.bind(this);

        mNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNameLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }


    /*------------------------------- Callbacks -------------------------------*/

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
