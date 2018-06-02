package com.troya.menuplanner.Temp.Recipes.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

public class RecipeIngredientDialog extends Dialog {
    public RecipeIngredientDialog(@NonNull Context context) {
        super(context);
    }

   /* private static final int LAYOUT = R.layout.dialog_add_ingredient;
    private static final int LAYOUT_HINT_ITEM = R.layout.auto_list_item;

    private static final int ERROR_MESSAGE_1 = R.string.ingredient_cant_be_empty;
    private static final int ERROR_MESSAGE_2 = R.string.unit_cant_be_selected_without_any_amount;

    private EditText mEditAmount;
    private AutoCompleteTextView mEditIngredientName;
    private AutoCompleteTextView mEditUnit;
    private EditText mEditComment;

    private long mRecipeId;
    private IngredientInRecipeEntity mRecipeIngredient;
    private RecipeIngredientView mRecipeIngredientView;

    public RecipeIngredientDialog(@NonNull Context context, long recipeId, RecipeIngredientView recipeIngredientView) {
        super(context);

        this.mRecipeId = recipeId;
        this.mRecipeIngredientView = recipeIngredientView;
        this.mRecipeIngredient = new IngredientInRecipeEntity();
        this.mRecipeIngredient.setId((recipeIngredientView != null) ? recipeIngredientView.getId() : null);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(LAYOUT);

        Button btnOk = findViewById(R.id.btn_ok);
        Button btnClose = findViewById(R.id.btn_cancel);
        btnOk.setOnClickListener(this);
        btnClose.setOnClickListener(this);

        mEditAmount = findViewById(R.id.editAmount);
        mEditIngredientName = findViewById(R.id.editIngredientName);
        mEditUnit = findViewById(R.id.editUnit);
        mEditComment = findViewById(R.id.editComment);

      *//*  ArrayAdapter<String> autoIngredient = new ArrayAdapter<>(this.getContext(),
                LAYOUT_HINT_ITEM, IngredientRepo.getAllNames(this.getContext()));
        mEditIngredientName.setAdapter(autoIngredient);
        mEditIngredientName.setThreshold(1);

        ArrayAdapter<String> autoUnit = new ArrayAdapter<>(this.getContext(),
                LAYOUT_HINT_ITEM, UnitRepo.getAllNames(this.getContext()));
        mEditUnit.setAdapter(autoUnit);
        mEditUnit.setThreshold(1);*//*

        if (mRecipeIngredientView != null) {
          *//*  String correctedAmount;
            if (mRecipeIngredientView.getAmount() != 0) {
                if (mRecipeIngredientView.getAmount() % 1 == 0) {
                    correctedAmount = String.format(Locale.getDefault(), "%d", (long) mRecipeIngredientView.getAmount());
                } else {
                    correctedAmount = String.format(Locale.getDefault(), "%s", mRecipeIngredientView.getAmount());
                }
                mEditAmount.setText(correctedAmount);
            }

            mEditIngredientName.setText(mRecipeIngredientView.getIngredientName());
            mEditUnit.setText(mRecipeIngredientView.getUnitName());
            mEditComment.setText(mRecipeIngredientView.getComment());*//*
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                if (!mEditIngredientName.getText().toString().isEmpty()) {
                    if (!mEditUnit.getText().toString().isEmpty() && mEditAmount.getText().toString().isEmpty()) {
                        Toast.makeText(this.getContext(), ERROR_MESSAGE_2, Toast.LENGTH_SHORT).show();
                    } else {
                       *//* IngredientRepo ingredientRepo = new IngredientRepo(
                                this.getContext(),
                                new IngredientEntity(mEditIngredientName.getText().toString()),
                                null);
                        long ingredientId = ingredientRepo.getOrCreateIngredientId();
                        @Nullable Long unitId = (mEditUnit.getText().toString().isEmpty()) ? null
                                : new UnitRepo(this.getContext(), new UnitEntity(mEditUnit.getText().toString())).getOrCreateUnitId();

                        mRecipeIngredient.setRecipeId(mRecipeId);
                        mRecipeIngredient.setIngredientId(ingredientId);
                        if (!TextUtils.isEmpty(mEditAmount.getText().toString())) {
                            mRecipeIngredient.setAmount(Float.parseFloat(mEditAmount.getText().toString()));
                        }
                        mRecipeIngredient.setUnitId(unitId);
                        mRecipeIngredient.setComment(mEditComment.getText().toString());

                        (new RecipeIngredientRepo(this.getContext())).saveRecipeIngredient(this.getContext(), mRecipeIngredient);

                        dismiss();*//*
                    }

                } else {
                    Toast.makeText(this.getContext(), ERROR_MESSAGE_1, Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.btn_cancel:
                dismiss();
                break;

            default:
                break;
        }
    }*/

}
