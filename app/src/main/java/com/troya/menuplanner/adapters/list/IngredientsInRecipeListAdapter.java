package com.troya.menuplanner.adapters.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.transition.TransitionManager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.troya.menuplanner.R;
import com.troya.menuplanner.helpers.FloatStringHelper;
import com.troya.menuplanner.model.views.IngredientInRecipeInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsInRecipeListAdapter extends RecyclerView.Adapter<IngredientsInRecipeListAdapter.ViewHolder> {
    private static final int LAYOUT = R.layout.list_ingredient_item;
    private static final int MENU = R.menu.popup_ingr_in_rec_options;

    private List<IngredientInRecipeInfo> mData;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private IngredientsListCallback mCallback;

    public IngredientsInRecipeListAdapter(Context context, IngredientsListCallback callback) {
        mContext = context;
        mCallback = callback;
    }

    public void setData(List<IngredientInRecipeInfo> ingredients) {
        this.mData = ingredients;
        notifyDataSetChanged();
    }

    public void addData(IngredientInRecipeInfo ingredient) {
        this.mData.add(ingredient);
        notifyItemInserted(this.mData.size() - 1);
    }

    public void changeData(IngredientInRecipeInfo ingredient) {
        this.mData.set(ingredient.getPosition(), ingredient);
        notifyItemChanged(ingredient.getPosition());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(LAYOUT, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setup(mData.get(position));

        holder.mCommentIndicator.setOnClickListener(view -> {
            holder.shouldExpand = !holder.shouldExpand;
            if (holder.shouldExpand) {
                holder.mCommentView.setVisibility(View.VISIBLE);
            } else {
                holder.mCommentView.setVisibility(View.GONE);
            }

            TransitionManager.beginDelayedTransition(mRecyclerView);
        });

        holder.mMoreOptionsButton.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(mContext, view);
            popup.getMenuInflater().inflate(MENU, popup.getMenu());
            popup.show();

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_edit:
                        mData.get(position).setPosition(position);
                        mCallback.onEdit(mData.get(position));
                        break;

                    case R.id.menu_delete:
                        mCallback.onDelete(mData.get(position).getId());
                        mData.remove(position);

                        //TODO: problem
                        notifyDataSetChanged();
                        //notifyItemRemoved(position);
                        break;
                }
                return false;
            });
        });
    }

    @Override
    public int getItemCount() {
        return (mData != null) ? mData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgIngredientPic)
        AppCompatImageView mIngrImage;
        @BindView(R.id.editIngredientName)
        TextView mIngrNameView;
        @BindView(R.id.editAmount)
        TextView mAmountView;
        @BindView(R.id.editUnit)
        TextView mUnitView;
        @BindView(R.id.editComment)
        TextView mCommentView;
        @BindView(R.id.ibtnComment)
        AppCompatImageView mCommentIndicator;
        @BindView(R.id.ibtnMoreOptions)
        AppCompatImageView mMoreOptionsButton;

        boolean shouldExpand = false;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, itemView);
        }

        void setup(IngredientInRecipeInfo ingredient) {
            mIngrNameView.setText(ingredient.getIngredientName());

            if (!TextUtils.isEmpty(ingredient.getImageSource())) {
                int drawableResId = IngredientsInRecipeListAdapter.this.mContext.getResources()
                        .getIdentifier(
                                ingredient.getImageSource(),
                                "drawable",
                                IngredientsInRecipeListAdapter.this.mContext.getPackageName());
                if (drawableResId > 0) {
                    mIngrImage.setImageResource(drawableResId);
                } else {
                    Bitmap bitmap = BitmapFactory.decodeFile(ingredient.getImageSource());
                    mIngrImage.setImageBitmap(bitmap);
                }
            }

            mAmountView.setText(FloatStringHelper.getCorrectedValue(ingredient.getAmount()));

            if (ingredient.getUnitName() != null) {
                mUnitView.setText(ingredient.getUnitName());
            }

            if (!TextUtils.isEmpty(ingredient.getComment())) {
                mCommentIndicator.setImageResource(R.drawable.ic_comment_outline_active);
            } else {
                mCommentIndicator.setImageResource(R.drawable.ic_comment_outline_inactive);
            }

            mCommentView.setText(ingredient.getComment());
        }
    }

    public interface IngredientsListCallback {
        void onDelete (int id);

        void onEdit (IngredientInRecipeInfo ingredient);
    }
}
