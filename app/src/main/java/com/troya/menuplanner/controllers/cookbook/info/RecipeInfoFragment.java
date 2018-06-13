package com.troya.menuplanner.controllers.cookbook.info;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.troya.menuplanner.App;
import com.troya.menuplanner.R;
import com.troya.menuplanner.adapters.tab.RecipeTabFragmentAdapter;
import com.troya.menuplanner.helpers.ImageHelper;
import com.troya.menuplanner.model.db.entity.RecipeEntity;
import com.troya.menuplanner.model.views.IngredientInRecipeInfo;
import com.troya.menuplanner.viewmodel.RecipeInfoViewModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RecipeInfoFragment extends Fragment {
    public static final String TAG = RecipeInfoFragment.class.getSimpleName();
    private static final int LAYOUT = R.layout.fragment_recipe_info;
    private static final int POPUP_MENU = R.menu.menu_recipe_info;

    private static final int REQUEST_IMAGE_SELECT = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 200;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    private int mRecipeId;
    private RecipeEntity mRecipe;

    public static RecipeInfoFragment newInstance(int recipeId) {
        RecipeInfoFragment fragment = new RecipeInfoFragment();
        Bundle args = new Bundle();
        args.putInt(RecipeInfoActivity.KEY_RECIPE_ID, recipeId);
        fragment.setArguments(args);
        return fragment;
    }

    private RecipeInfoViewModel mViewModel;
    private Unbinder mUnbinder;
    private List<IngredientInRecipeInfo> mIngredientsInfo;

    @BindView(R.id.tbControls)
    Toolbar mToolbar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.editRecipeName)
    EditText mRecipeNameView;
    @BindView(R.id.editSource)
    EditText mSourceView;
    @BindView(R.id.imgRecipePhoto)
    ImageView mImageView;
    @BindView(R.id.ibtnMore)
    ImageView mMoreOptionsButton;

    private void setToolbarData() {
        if (this.getActivity() != null) {
            // Header toolbar
            mRecipeNameView.setText(mRecipe.getName());
            if (TextUtils.isEmpty(mRecipe.getSource())) {
                mSourceView.setVisibility(View.GONE);
            } else {
                mSourceView.setVisibility(View.VISIBLE);
                mSourceView.setText(mRecipe.getSource());
            }
        }
    }

    private void setRecipeImage() {
        Bitmap bitmap = ImageHelper.getImageBitmap(mRecipe.getImage());
        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
        } else {
            mImageView.setImageResource(R.drawable.recipe_bckg);
        }
    }

    private void initTabLayout() {
        if (this.getActivity() != null) {
            RecipeTabFragmentAdapter adapter = new RecipeTabFragmentAdapter(this.getContext(),
                    this.getActivity().getSupportFragmentManager(), mRecipe);
            mViewPager.setAdapter(adapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }

    @OnClick(R.id.ibtnMore)
    void onMoreOptionsClick() {
        showPopupMenu();
    }

    @OnClick(R.id.ibtnConfirm)
    void onConfirmClick() {
        mViewModel.saveRecipeChanges(mRecipe);
    }

    @OnClick(R.id.ibtnCancel)
    void onCancelClick() {

    }

    private void showPopupMenu() {
        if (this.getActivity() != null) {
            PopupMenu popup = new PopupMenu(this.getContext(), mMoreOptionsButton);
            popup.getMenuInflater().inflate(POPUP_MENU, popup.getMenu());
            popup.show();
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_change_photo:
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, REQUEST_IMAGE_SELECT);
                        break;
                }

                return false;
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_SELECT:
                    if (this.getContext() != null) {
                        try {
                            if (data.getData() != null) {
                                InputStream inputStream = this.getContext().getContentResolver().openInputStream(data.getData());
                                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                mImageView.setImageBitmap(bitmap);
                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                mRecipe.setImage(outputStream.toByteArray());
                            } else {
                                Log.i(TAG, "onActivityResult: no Uri");
                            }
                        } catch (FileNotFoundException e) {
                            Log.i(TAG, "onActivityResult: " + e.getMessage());
                        }
                    }
                    break;
            }
        }
    }


    /*------------------------------- Lifecycle -------------------------------*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecipeInfoActivity) {
            ((RecipeInfoActivity) context).setRecipeInfoFragmentTag(getTag());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipeId = getArguments().getInt(RecipeInfoActivity.KEY_RECIPE_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null)
            ((App) getActivity().getApplication())
                    .getAppComponent()
                    .inject(this);

        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(RecipeInfoViewModel.class);


        mViewModel.getRecipeById(mRecipeId).observe(this, recipe -> {
            if (recipe != null) {
                mRecipe = recipe;
                setRecipeImage();
                setToolbarData();
                initTabLayout();
            }
        });
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }


    /*------------------------------- Callbacks -------------------------------*/

    public void addIngredient(IngredientInRecipeInfo ingredient) {

    }

    public void setInstructions(String instructions) {
        mRecipe.setComment(instructions);
    }

    public void setResultAmount(float resultAmount) {
        mRecipe.setResultAmount(resultAmount);
    }

    public void setResultUnit(String unit) {
        int unitId = mViewModel.getUnitIdByName(unit);
        mRecipe.setResultUnitId((unitId != 0) ? unitId : null);
    }

    public void setIngredientsInfo(List<IngredientInRecipeInfo> ingredientsInfo) {
        mIngredientsInfo = ingredientsInfo;
    }
}
