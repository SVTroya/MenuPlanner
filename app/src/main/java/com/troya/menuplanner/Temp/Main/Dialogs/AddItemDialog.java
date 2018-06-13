package com.troya.menuplanner.Temp.Main.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

public class AddItemDialog extends Dialog {
    public AddItemDialog(@NonNull Context context) {
        super(context);
    }
    /*
    private static final int LAYOUT = R.layout.dialog_add_item;

    public interface OnPositiveListener {
        void OnPositive();
    }

    private OnPositiveListener mOnPositiveListener;
    private ItemRepo mItem;

    protected Button mBtnOk;
    protected Button mBtnClose;
    protected EditText mEditItemName;

    public AddRecipeDialog(Context context, ItemRepo item, OnPositiveListener onPositiveListener) {
        super(context);
        mOnPositiveListener = onPositiveListener;
        mItem = item;
    }

    public EditText getEditItemName() {
        return mEditItemName;
    }

    public Button getBtnOk() {
        return mBtnOk;
    }

    public Button getBtnClose() {
        return mBtnClose;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.onCreate(savedInstanceState, LAYOUT);
    }

    protected void onCreate(Bundle savedInstanceState, @LayoutRes int layout) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(getWindow() != null) {
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        setContentView(layout);
        mBtnOk = findViewById(R.id.btn_ok);
        mBtnClose = findViewById(R.id.btn_cancel);
        mEditItemName = findViewById(R.id.editItemName);

        mBtnOk.setOnClickListener(new View.Callback() {
            @Override
            public void onClick(View view) {
                if (mItem.getItem() != null) {
                    mItem.getItem().setName(getEditItemName().getText().toString());
                } else {
                    mItem.createNewItem(getEditItemName().getText().toString());
                }

                if (mItem instanceof GroupedItemRepo && AddRecipeDialog.this instanceof AddItemWithGroupDialog) {
                    String groupName = ((AddItemWithGroupDialog) AddRecipeDialog.this).getEditGroupName().getText().toString();
                    ((GroupedItemRepo) mItem).setGroupName(groupName);
                }

                if (!TextUtils.isEmpty(mItem.getItem().getName())) {
                    try {
                        long newItemId = mItem.addItems();
                        mOnPositiveListener.OnPositive();
                        dismiss();

                        switch (mItem.getTag()) {
                            case Repository.TAG:
                                if (newItemId > 0) {
                                    Intent intent = new Intent(getContext(), RecipeDescriptionActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putLong(RecipeRepo.KEY_SELECTED_RECIPE_ID, newItemId);
                                    intent.putExtras(bundle);
                                    getContext().startActivity(intent);
                                }
                                break;
                        }
                    } catch (SQLiteConstraintException ex) {
                        if (DBHelper.getErrorCode(ex) == DBHelper.SQLITE_CONSTRAINT_UNIQUE) {
                            Toast.makeText(
                                    getContext(),
                                    mItem.getTag() + " " + getContext().getString(R.string.with_the_same_name_already_exists),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            throw ex;
                        }
                    }
                } else {
                    Toast.makeText
                            (getContext(),
                                    mItem.getTag() + " " + getContext().getString(R.string.name_cant_be_empty),
                                    Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/
}
