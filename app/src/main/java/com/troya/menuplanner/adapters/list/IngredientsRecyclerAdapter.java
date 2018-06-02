package com.troya.menuplanner.adapters.list;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.troya.menuplanner.adapters.list.IngredientsRecyclerAdapter.IngrGroupViewHolder;
import com.troya.menuplanner.adapters.list.IngredientsRecyclerAdapter.IngrViewHolder;
import com.troya.menuplanner.model.db.entity.IngredientEntity;
import com.troya.menuplanner.model.db.entity.IngredientGroupEntity;
import com.troya.menuplanner.R;

import java.util.List;

public class IngredientsRecyclerAdapter extends ExpRecyclerAdapter<IngrGroupViewHolder, IngrViewHolder> {

    private static final int GROUP_ITEM_LAYOUT = R.layout.list_group_manager_item;
    private static final int CHILD_ITEM_LAYOUT = R.layout.list_item_manager_item;
    private static final int SELECTED_ITEM_MARGIN_DP = 10;
    private static final int ITEM_MARGIN_DP = 1;

    private int mSelectedItemMargin;
    private int mItemMargin;

    public IngredientsRecyclerAdapter(Context context, List<Item> data) {
        super(data);
        mSelectedItemMargin = getPx(context, SELECTED_ITEM_MARGIN_DP);
        mItemMargin = getPx(context, ITEM_MARGIN_DP);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == getSelectedItem() || position == getPreviouslySelectedItem()) {
            LinearLayout layout = (LinearLayout) holder.itemView;
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) layout.getLayoutParams();

            if (getSelectedItem() == position) {
                layoutParams.bottomMargin = mSelectedItemMargin;
                layoutParams.topMargin = mSelectedItemMargin;
                if (getItemViewType(position) == PARENT) {
                    ((IngrGroupViewHolder)holder).getTxtGroupName().setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                }
                else {
                    ((IngrViewHolder)holder).getTxtIngrName().setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                }
            }
            if (getPreviouslySelectedItem() == position) {
                layoutParams.bottomMargin = mItemMargin;
                layoutParams.topMargin = mItemMargin;
                if (getItemViewType(position) == PARENT) {
                    ((IngrGroupViewHolder)holder).getTxtGroupName().setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                }
                else {
                    ((IngrViewHolder)holder).getTxtIngrName().setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                }
            }
        }

        super.onBindViewHolder(holder, position);
    }

    @Override
    IngrGroupViewHolder initializeParentViewHolder(LayoutInflater layoutInflater, ViewGroup parent) {
        return new IngrGroupViewHolder(layoutInflater.inflate(GROUP_ITEM_LAYOUT, parent, false));
    }

    @Override
    IngrViewHolder initializeChildViewHolder(LayoutInflater layoutInflater, ViewGroup parent) {
        return new IngrViewHolder(layoutInflater.inflate(CHILD_ITEM_LAYOUT, parent, false));
    }

    static class IngrGroupViewHolder extends ExpRecyclerAdapter.ParentViewHolder<IngredientGroupEntity> {

        private TextView mTxtGroupName;

        public IngrGroupViewHolder(View itemView) {
            super(itemView, R.id.imgToggle);
            mTxtGroupName = itemView.findViewById(R.id.editGroupTitle);
        }

        public TextView getTxtGroupName() {
            return mTxtGroupName;
        }


        @Override
        public void setData(IngredientGroupEntity data) {
            getTxtGroupName().setTypeface(null, Typeface.BOLD);
            getTxtGroupName().setText(data.getName());
        }
    }

    static class IngrViewHolder extends ExpRecyclerAdapter.ChildViewHolder<IngredientEntity> {
        private TextView mTxtIngrName;

        public IngrViewHolder(View itemView) {
            super(itemView);
            mTxtIngrName = itemView.findViewById(R.id.editChildTitle);
        }

        public TextView getTxtIngrName() {
            return mTxtIngrName;
        }

        @Override
        public void setData(IngredientEntity data) {
            getTxtIngrName().setText(data.getName());
        }
    }
}

