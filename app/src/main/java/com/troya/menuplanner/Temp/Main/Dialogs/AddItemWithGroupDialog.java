package com.troya.menuplanner.Temp.Main.Dialogs;

import android.content.Context;
import android.support.annotation.NonNull;

public class AddItemWithGroupDialog extends AddItemDialog {
    public AddItemWithGroupDialog(@NonNull Context context) {
        super(context);
    }

    /*
    private static final int LAYOUT = R.layout.dialog_add_item_with_group;
    public static final int LAYOUT_HINT_ITEM = R.layout.auto_list_item;

    private AutoCompleteTextView mEditGroupName;

    public AddItemWithGroupDialog(@NonNull Context context, GroupedItemRepo item, OnPositiveListener onPositiveListener) {
        super(context, item, onPositiveListener);
    }

    public AutoCompleteTextView getEditGroupName() {
        return mEditGroupName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, LAYOUT);
        mEditGroupName = findViewById(R.id.editGroupName);
    }*/
}
