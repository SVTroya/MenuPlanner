package com.troya.menuplanner.controllers.cookbook.info.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.troya.menuplanner.R;
import com.troya.menuplanner.helpers.FloatStringHelper;
import com.troya.menuplanner.model.views.IngredientInRecipeInfo;
import com.troya.menuplanner.viewmodel.IngredientsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IngredientInRecipeDialog extends Dialog {
    private static final int LAYOUT = R.layout.dialog_add_ingredient;
    private static final int LAYOUT_HINT_ITEM = R.layout.auto_list_item;

    private static final int ERROR_MESSAGE_1 = R.string.ingredient_cant_be_empty;
    private static final int ERROR_MESSAGE_2 = R.string.unit_cant_be_selected_without_any_amount;

    private DialogCallback mCallback;
    private IngredientsViewModel mViewModel;

    public IngredientInRecipeDialog(@NonNull Context context, IngredientInRecipeInfo ingredient,
                                    DialogCallback callback, IngredientsViewModel viewModel) {
        super(context);

        this.mIngredient = (ingredient == null) ? new IngredientInRecipeInfo() : ingredient;
        this.mCallback = callback;
        this.mViewModel = viewModel;
    }

    @BindView(R.id.tilAmount)
    TextInputLayout mAmountTil;
    @BindView(R.id.editAmount)
    EditText mAmountView;
    @BindView(R.id.tilIngredientName)
    TextInputLayout mIngredientNameTil;
    @BindView(R.id.editIngredientName)
    AutoCompleteTextView mIngredientNameView;
    @BindView(R.id.tilUnit)
    TextInputLayout mUnitTil;
    @BindView(R.id.editUnit)
    AutoCompleteTextView mUnitView;
    @BindView(R.id.editComment)
    EditText mCommentView;

    private IngredientInRecipeInfo mIngredient;

    public interface DialogCallback {
        void onVerifiedDismiss(IngredientInRecipeInfo ingredient);

        String[] getAllIngredients();

        String[] getAllUnits();
    }

    @OnClick(R.id.btn_ok)
    void onOkClick() {
        if (verifyData()) {
            mIngredient.setIngredientName(mIngredientNameView.getText().toString());
            mIngredient.setUnitName(mUnitView.getText().toString());
            mIngredient.setComment(mCommentView.getText().toString());
            if (!TextUtils.isEmpty(mAmountView.getText().toString())) {
                mIngredient.setAmount(Float.parseFloat(mAmountView.getText().toString()));
            }

            mCallback.onVerifiedDismiss(mIngredient);
            dismiss();
        }
    }

    @OnClick(R.id.btn_cancel)
    void onCancelClick() {
        dismiss();
    }

    private boolean verifyData() {
        boolean result = true;
        mAmountTil.setError(null);
        mIngredientNameTil.setError(null);
        mUnitTil.setError(null);

        if (!mUnitView.getText().toString().isEmpty() && mAmountView.getText().toString().isEmpty()) {
            mIngredientNameTil.setError(this.getContext().getString(ERROR_MESSAGE_2));
            result = false;
        }
        if (TextUtils.isEmpty(mIngredientNameView.getText().toString())) {
            mIngredientNameTil.setError(this.getContext().getString(ERROR_MESSAGE_1));
            result = false;
        }

        return result;
    }

    /*------------------------------- Lifecycle -------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(LAYOUT);

        ButterKnife.bind(this);

        ArrayAdapter<String> autoIngredient = new ArrayAdapter<>(this.getContext(),
                LAYOUT_HINT_ITEM, mViewModel.getAllIngredientNames());
        mIngredientNameView.setAdapter(autoIngredient);
        mIngredientNameView.setThreshold(1);

        ArrayAdapter<String> autoUnit = new ArrayAdapter<>(this.getContext(),
                LAYOUT_HINT_ITEM, mViewModel.getAllUnitNames());
        mUnitView.setAdapter(autoUnit);
        mUnitView.setThreshold(1);

        mAmountView.setText(FloatStringHelper.getCorrectedValue(mIngredient.getAmount()));
        mIngredientNameView.setText(mIngredient.getIngredientName());
        mUnitView.setText(mIngredient.getUnitName());
        mCommentView.setText(mIngredient.getComment());
    }
}
