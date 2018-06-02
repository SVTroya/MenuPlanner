package com.troya.menuplanner.adapters.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.troya.menuplanner.R;

public class RecipeIngredientsListAdapter extends Adapter<RecipeIngredientsListAdapter.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /*
        private static final int INGREDIENT_LAYOUT = R.layout.list_ingredient_item;

        private Context mContext;
        private Cursor mCursor;
        private long mRecipeId;

        public RecipeIngredientsListAdapter(Context context, Cursor cursor, long recipeId) {
            mContext = context;
            mCursor = cursor;
            mRecipeId = recipeId;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(INGREDIENT_LAYOUT, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            if (mCursor != null) {
                if (mCursor.moveToPosition(position)) {
                    final RecipeIngredientView ingredientInfo = RecipeIngredientView.generateObjectFromCursor(mCursor);
                    holder.getIngrNameView().setText(ingredientInfo.getIngredientName());

                    if (ingredientInfo.getAmount() != 0) {
                        String correctedAmount;
                        if (ingredientInfo.getAmount() % 1 == 0) {
                            correctedAmount = String.format(Locale.getDefault(), "%d", (long) ingredientInfo.getAmount());
                        } else {
                            correctedAmount = String.format(Locale.getDefault(), "%s", ingredientInfo.getAmount());
                        }

                        holder.getAmountView().setText(correctedAmount);
                    }

                    if (ingredientInfo.getUnitName() != null) {
                        holder.getUnitView().setText(ingredientInfo.getUnitName());
                    }

                    holder.getCommentView().setText(ingredientInfo.getComment());
                    holder.getDeleteButtonView().setOnClickListener(new DeleteIngrOnClickListener(mContext, mRecipeId, ingredientInfo.getId()));
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {

                            RecipeIngredientDialog dlg = new RecipeIngredientDialog(mContext,
                                    mRecipeId, ingredientInfo);

                            dlg.show();

                            return true;
                        }
                    });
                } else {
                    throw new SQLiteException("Can't move to position " + position);
                }
            }
        }

        @Override
        public int getItemCount() {
            return (mCursor != null) ? mCursor.getCount() : 0;
        }
    */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvIngrName;
        private final TextView tvAmount;
        private final TextView tvUnit;
        private final TextView tvComment;
        private final ImageButton imgbDelete;

        public ViewHolder(View view) {
            super(view);
            tvIngrName = view.findViewById(R.id.editIngredientName);
            tvAmount = view.findViewById(R.id.editAmount);
            tvUnit = view.findViewById(R.id.editUnit);
            tvComment = view.findViewById(R.id.editComment);
            imgbDelete = view.findViewById(R.id.imgBtnDelete);
        }

        public TextView getIngrNameView() {
            return tvIngrName;
        }

        public TextView getAmountView() {
            return tvAmount;
        }

        public TextView getUnitView() {
            return tvUnit;
        }

        public TextView getCommentView() {
            return tvComment;
        }

        public ImageButton getDeleteButtonView() {
            return imgbDelete;
        }
    }

    /*
    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        Cursor oldCursor = mCursor;
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeRemoved(0, getItemCount());
        }
        return oldCursor;
    }*/
}
