package com.troya.menuplanner.adapters.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.troya.menuplanner.R;
import com.troya.menuplanner.helpers.FloatStringHelper;
import com.troya.menuplanner.model.views.IngredientInRecipeInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsInRecipeListAdapter extends RecyclerView.Adapter<IngredientsInRecipeListAdapter.ViewHolder> {
    private static final int LAYOUT = R.layout.list_ingredient_item;

    private List<IngredientInRecipeInfo> mData;
    private Context mContext;
    private RecyclerView mRecyclerView;

    public IngredientsInRecipeListAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<IngredientInRecipeInfo> ingredients) {
        this.mData = ingredients;
        notifyDataSetChanged();
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
    }

    @Override
    public int getItemCount() {
        return (mData != null) ? mData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgIngredientPic)
        ImageView mIngrImage;
        @BindView(R.id.editIngredientName)
        TextView mIngrNameView;
        @BindView(R.id.editAmount)
        TextView mAmountView;
        @BindView(R.id.editUnit)
        TextView mUnitView;
        @BindView(R.id.editComment)
        TextView mCommentView;
        @BindView(R.id.ibtnComment)
        ImageView mCommentIndicator;
        @BindView(R.id.ibtnMoreOptions)
        ImageView mMoreOptionsButton;

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
            }
            else {
                mCommentIndicator.setImageResource(R.drawable.ic_comment_outline_inactive);
            }

            mCommentView.setText(ingredient.getComment());
        }
    }
}
