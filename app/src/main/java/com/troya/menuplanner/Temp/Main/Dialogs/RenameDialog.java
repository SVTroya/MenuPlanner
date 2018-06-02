package com.troya.menuplanner.Temp.Main.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

public class RenameDialog extends Dialog {


    public RenameDialog(@NonNull Context context) {
        super(context);
    }

    /* private BaseItem mItemToRename;
    private EditText mEditName;

    public RenameDialog(Context context, BaseItem itemToRename) {
        super(context);
        this.mItemToRename = itemToRename;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.rename);
        if(getWindow() != null) {
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        setContentView(R.layout.dialog_rename);
        Button btnOk = findViewById(R.id.btn_ok);
        Button btnClose = findViewById(R.id.btn_cancel);
        mEditName = findViewById(R.id.editName);
        mEditName.setText(mItemToRename.getName());
        btnOk.setOnClickListener(this);
        btnClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                if (ItemRepo.rename(getContext(), this.mItemToRename, mEditName.getText().toString())) {
                    dismiss();
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
