package com.troya.menuplanner.adapters.list;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.troya.menuplanner.R;
import com.troya.menuplanner.adapters.list.RecipesListAdapter.RecipeViewHolder;
import com.troya.menuplanner.helpers.ImageHelper;
import com.troya.menuplanner.model.db.entity.CategoryEntity;
import com.troya.menuplanner.model.views.RecipeInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesListAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private static final int LAYOUT = R.layout.card_recipe_item;

    private Callback mCallback;

    private List<RecipeInfo> mData;
    private List<CategoryEntity> mCategories;

    public interface Callback {
        void onClick(int id);
    }

    public RecipesListAdapter(@NonNull Callback callback) {
        mCallback = callback;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(LAYOUT, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return mData.get(position).getId();
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.setup(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return (mData != null) ? mData.size() : 0;
    }

    public void setData(List<RecipeInfo> newData) {
        this.mData = newData;
        notifyDataSetChanged();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgRecipePhoto)
        AppCompatImageView mRecipePhotoImage;
        @BindView(R.id.txtRecipeName)
        TextView mRecipeNameText;
        @BindView(R.id.txtRecipeSource)
        TextView mRecipeSourceText;
        @BindView(R.id.ratingBar)
        RatingBar mRatingBar;
        @BindView(R.id.imgOptions)
        AppCompatImageView mOptionsButton;

        /*@BindView(R.id.tagView)
        TagView mTagGroup;*/

      /*  @OnClick(R.id.imgOptions)
        void onOptionClick() {
            PopupMenu popupMenu = new PopupMenu(RecipesListAdapter.this.mContext, mOptionsButton);
            popupMenu.getMenuInflater().inflate(R.menu.popup_recipe_options, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return false;
                }
            });

            popupMenu.show();
        }*/

        RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onClick((int)RecipesListAdapter.this.getItemId(getAdapterPosition()));
                }
            });
        }

        void setup(RecipeInfo recipe) {
            Bitmap bitmap = ImageHelper.getImageBitmap(recipe.getImage());
            if (bitmap != null) {
                mRecipePhotoImage.setImageBitmap(bitmap);
            } else {
                mRecipePhotoImage.setImageResource(R.drawable.recipe_card_image);
            }
            mRecipeNameText.setText(recipe.getName());
            mRecipeSourceText.setText(recipe.getSource());
            mRatingBar.setRating(recipe.getRating());

            /*  CustomTag tag = new CustomTag(5, "Ve", 0xFF178F19);
        holder.mTagGroup.addTag(tag);
        CustomTag tag2 = new CustomTag(6, "De", 0xFFF05800);
        holder.mTagGroup.addTag(tag2);
        CustomTag tag3 = new CustomTag(6, "Di", 0xFF27D1D4);
        holder.mTagGroup.addTag(tag3);*/
        }
    }
}
