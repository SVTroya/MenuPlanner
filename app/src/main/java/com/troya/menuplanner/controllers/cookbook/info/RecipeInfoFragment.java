package com.troya.menuplanner.controllers.cookbook.info;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.troya.menuplanner.R;
import com.troya.menuplanner.adapters.tab.RecipeTabFragmentAdapter;
import com.troya.menuplanner.controllers.dialogs.SetNameDialog;
import com.troya.menuplanner.helpers.ImageHelper;
import com.troya.menuplanner.helpers.RecipeInfoTabsCallback;
import com.troya.menuplanner.model.db.entity.RecipeEntity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RecipeInfoFragment extends Fragment implements SetNameDialog.OnPositiveListener {
    public static final String TAG = RecipeInfoFragment.class.getSimpleName();
    private static final int LAYOUT = R.layout.fragment_recipe_info;
    private static final int POPUP_MENU = R.menu.menu_recipe_info;

    private static final int REQUEST_IMAGE_SELECT = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 200;
    private static final int REQUEST_IMAGE_CROP = 300;

    private RecipeEntity mRecipe;
    private RecipeInfoTabsCallback mInfoTabsCallback;
    private RecipeInfoFragmentListener mCallback;

    public static RecipeInfoFragment newInstance(RecipeEntity recipe) {
        RecipeInfoFragment fragment = new RecipeInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(RecipeInfoActivity.KEY_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    private Unbinder mUnbinder;

    @BindView(R.id.tbActionBar)
    Toolbar mToolbar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.txtRecipeName)
    TextView mRecipeNameView;
    @BindView(R.id.txtSource)
    TextView mSourceView;
    @BindView(R.id.imgRecipePhoto)
    AppCompatImageView mImageView;
    @BindView(R.id.ibtnMore)
    AppCompatImageView mMoreOptionsButton;

    @BindView(R.id.fabAddNew)
    FloatingActionButton mAddIngredientButton;

    public interface RecipeInfoFragmentListener {
        void onSave();

        void onRecipeNameChanged(String newRecipeName);

        void onSourceChanged(String newSource);
    }

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

            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    switch (position) {
                        case 0:
                            mAddIngredientButton.show();
                            break;

                        default:
                            mAddIngredientButton.hide();
                            break;
                    }
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    private void showPopupMenu() {
        if (this.getActivity() != null) {
            PopupMenu popup = new PopupMenu(this.getContext(), mMoreOptionsButton);
            popup.getMenuInflater().inflate(POPUP_MENU, popup.getMenu());
            popup.show();
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_change_photo:
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                            galleryIntent.setType("image/*");
                            startActivityForResult(galleryIntent, REQUEST_IMAGE_SELECT);
                            break;
                        case R.id.menu_rename:
                            if (getContext() != null) {
                                SetNameDialog setNameDialog = new SetNameDialog(
                                        getContext(),
                                        mRecipe.getName(),
                                        R.string.new_recipe_name,
                                        RecipeInfoFragment.this);
                                setNameDialog.setOnDismissListener(dialog -> {
                                    if (((SetNameDialog) dialog).isPassed()
                                            && !mRecipe.getName().equals(((SetNameDialog) dialog).getText())) {
                                        mCallback.onRecipeNameChanged(((SetNameDialog) dialog).getText());
                                    }
                                });
                                setNameDialog.show();
                            }
                            break;
                        case R.id.menu_change_source:
                            if (getContext() != null) {
                                SetNameDialog setNameDialog = new SetNameDialog(
                                        getContext(),
                                        mRecipe.getSource(),
                                        R.string.new_source_name,
                                        RecipeInfoFragment.this);
                                setNameDialog.setOnDismissListener(dialog -> {
                                    if (((SetNameDialog) dialog).isPassed()
                                            && !(((SetNameDialog)dialog).getText()).equals(mRecipe.getSource())) {
                                        mCallback.onSourceChanged(((SetNameDialog) dialog).getText());
                                    }
                                });
                                setNameDialog.show();
                            }
                            break;
                    }

                    return false;
                }
            });
        }
    }

    private void ImageCropFunction(Uri uri) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        cropIntent.setDataAndType(uri, "image/*");

        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("outputX", 400);
        cropIntent.putExtra("outputY", 300);
        cropIntent.putExtra("aspectX", 4);
        cropIntent.putExtra("aspectY", 3);
        cropIntent.putExtra("return-data", true);

        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }


    /*------------------------------- Lifecycle -------------------------------*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecipeInfoActivity) {
            ((RecipeInfoActivity) context).setRecipeInfoFragmentTag(getTag());
        }

        try {
            mInfoTabsCallback = (RecipeInfoTabsCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " +
                    RecipeInfoTabsCallback.class.getSimpleName());
        }

        try {
            mCallback = (RecipeInfoFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " +
                    RecipeInfoTabsCallback.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(RecipeInfoActivity.KEY_RECIPE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        if (mRecipe != null) {
            setRecipeImage();
            setToolbarData();
            initTabLayout();
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }


    /*------------------------------- Callbacks -------------------------------*/

    @OnClick(R.id.ibtnMore)
    void onMoreOptionsClick() {
        showPopupMenu();
    }

    @OnClick(R.id.ibtnSave)
    void onSaveClick() {
        mCallback.onSave();
    }

    @OnClick(R.id.ibtnCancel)
    void onCancelClick() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_SELECT:
                    if (this.getContext() != null) {
                        /*try {*/
                        if (data.getData() != null) {
                            InputStream inputStream = null;
                            try {
                                inputStream = this.getContext().getContentResolver().openInputStream(data.getData());
                                inputStream.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ImageCropFunction(data.getData());
                               /* InputStream inputStream = this.getContext().getContentResolver().openInputStream(data.getData());
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                                mRecipe.setImage(outputStream.toByteArray());
                                mImageView.setImageBitmap(ImageHelper.getImageBitmap(mRecipe.getImage()));
                                mIsRecipeInfoChanged = true;*/

                        } else {
                            Log.i(TAG, "onActivityResult: no Uri");
                        }/*
                        } catch (IOException e) {
                            Log.i(TAG, "onActivityResult: " + e.getMessage());
                        }*/
                    }
                    break;
                case REQUEST_IMAGE_CROP:
                    if (data != null) {

                        Bundle bundle = data.getExtras();
                        Bitmap bitmap = bundle.getParcelable("data");

                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        mRecipe.setImage(outputStream.toByteArray());
                        mImageView.setImageBitmap(ImageHelper.getImageBitmap(mRecipe.getImage()));
                        ((RecipeInfoActivity) getActivity()).setRecipeInfoChanged(true);
                    }
                    break;
            }
        }
    }

    @OnClick(R.id.fabAddNew)
    void onAddIngredientClick() {
        mInfoTabsCallback.onAddIngredientClick(null);
    }

    @Override
    public String onPositive(String text) {
        return null;
    }
}
