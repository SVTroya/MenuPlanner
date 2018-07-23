package com.troya.menuplanner.controllers.cookbook.info;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.SparseArray;

import com.troya.menuplanner.App;
import com.troya.menuplanner.R;
import com.troya.menuplanner.helpers.AppSnackbar;
import com.troya.menuplanner.helpers.RecipeInfoTabsCallback;
import com.troya.menuplanner.model.db.entity.IngredientInRecipeEntity;
import com.troya.menuplanner.model.db.entity.RecipeEntity;
import com.troya.menuplanner.model.views.IngredientInRecipeInfo;
import com.troya.menuplanner.viewmodel.RecipeInfoViewModel;
import com.troya.menuplanner.controllers.cookbook.info.RecipeInfoFragment.RecipeInfoFragmentListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RecipeInfoActivity extends FragmentActivity implements RecipeInfoTabsCallback, RecipeInfoFragmentListener {

    public static final int LAYOUT = R.layout.activity_recipe_info;

    public static final String KEY_RECIPE_ID = "recipe_id";
    public static final String KEY_RECIPE = "recipe";

    private int mRecipeId;

    private String mRecipeInfoFragmentTag;
    private String mIngredientsFragmentTag;
    private String mDetailsFragmentTag;


    private FragmentManager mFragmentManager;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    private RecipeInfoViewModel mViewModel;


    private RecipeEntity mRecipe;
    private List<IngredientInRecipeInfo> mIngredientsToSaveInfo;
    private List<Integer> mDeletedIngredientsIds;
    SparseArray<String> mNewIngredientsData = null;
    SparseArray<String> mNewUnitsData = null;
    private boolean mIsRecipeInfoChanged = false;

    private List<IngredientInRecipeEntity> prepareIngredientsData() {
        if (mIngredientsToSaveInfo != null) {
            List<IngredientInRecipeEntity> ingredientsInRecipeForDb = new ArrayList<>();


            for (int i = 0; i < mIngredientsToSaveInfo.size(); i++) {
                IngredientInRecipeInfo ingredient = mIngredientsToSaveInfo.get(i);
                IngredientInRecipeEntity ingrInRecEntity = new IngredientInRecipeEntity();

                if (ingredient.getId() != null) {
                    ingrInRecEntity.setId(ingredient.getId());
                }
                ingrInRecEntity.setRecipeId(mRecipeId);
                ingrInRecEntity.setAmount(ingredient.getAmount() != null ? ingredient.getAmount() : 0);
                ingrInRecEntity.setInstructions(ingredient.getComment());

                if (ingredient.getIngredientId() != null) {
                    ingrInRecEntity.setIngredientId(ingredient.getIngredientId());
                } else {
                    if (mNewIngredientsData == null) {
                        mNewIngredientsData = new SparseArray<>();
                    }
                    mNewIngredientsData.append(i, ingredient.getIngredientName());
                }

                if (ingredient.getUnitId() != null) {
                    ingrInRecEntity.setUnitId(ingredient.getUnitId());
                } else if (!TextUtils.isEmpty(ingredient.getUnitName())) {
                    if (mNewUnitsData == null) {
                        mNewUnitsData = new SparseArray<>();
                    }
                    mNewUnitsData.append(i, ingredient.getUnitName());
                }

                ingredientsInRecipeForDb.add(ingrInRecEntity);
            }

            return ingredientsInRecipeForDb;
        } else {
            return null;
        }
    }

    private void prepareRecipeData() {
        String resultUnitName = getResultUnitName();
        if (!TextUtils.isEmpty(resultUnitName) && mRecipe.getResultUnitId() == null) {
            SparseArray<String> unit = new SparseArray<>();
            unit.append(0, resultUnitName);
            mRecipe.setResultUnitId(mViewModel.getOrCreateUnitIds(unit).valueAt(0));
        }
    }

    public void setRecipeInfoChanged(boolean recipeInfoChanged) {
        mIsRecipeInfoChanged = recipeInfoChanged;
    }

    public void setRecipeInfoFragmentTag(String tag) {
        mRecipeInfoFragmentTag = tag;
    }

    public void setIngredientsFragmentTag(String tag) {
        mIngredientsFragmentTag = tag;
    }

    public void setDetailsFragmentTag(String tag) {
        mDetailsFragmentTag = tag;
    }

    private RecipeInfoFragment getRecipeInfoFragment() {
        try {
            return (RecipeInfoFragment) mFragmentManager.findFragmentByTag(mRecipeInfoFragmentTag);
        } catch (Exception ex) {
            return null;
        }
    }

    private IngredientsFragment getIngredientsFragment() {
        try {
            return (IngredientsFragment) mFragmentManager.findFragmentByTag(mIngredientsFragmentTag);
        } catch (Exception ex) {
            return null;
        }
    }

    private DetailsFragment getDetailsFragment() {
        try {
            return (DetailsFragment) mFragmentManager.findFragmentByTag(mDetailsFragmentTag);
        } catch (Exception ex) {
            return null;
        }
    }

    /*------------------------------- Lifecycle -------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);


        mRecipeId = getIntent().getIntExtra(KEY_RECIPE_ID, 0);
        mFragmentManager = getSupportFragmentManager();

    }

    @Override
    protected void onStart() {
        ((App)getApplication()).getAppComponent().inject(this);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(RecipeInfoViewModel.class);



        mViewModel.getRecipeById(mRecipeId).observe(this, recipe -> {
            if (recipe != null) {
                mRecipe = recipe;
                RecipeInfoFragment recipeInfoFragment = RecipeInfoFragment.newInstance(mRecipe);
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                transaction.replace(R.id.fragment, recipeInfoFragment, RecipeInfoFragment.TAG);
                transaction.commit();
            }
        });
        super.onStart();
    }

    /*------------------------------- Callbacks -------------------------------*/

    @Override
    public void addIngredientInfo(IngredientInRecipeInfo ingredient) {
        if (mIngredientsToSaveInfo == null) {
            mIngredientsToSaveInfo = new ArrayList<>();
        }
        mIngredientsToSaveInfo.add(ingredient);
    }

    @Override
    public void setInstructions(String instructions) {
        mRecipe.setComment(instructions);
        mIsRecipeInfoChanged = true;
    }

    @Override
    public void setResultAmount(float resultAmount) {
        mRecipe.setResultAmount(resultAmount);
        mIsRecipeInfoChanged = true;
    }

    @Override
    public void setResultUnit(String unitName) {
        IngredientsFragment ingredientsFragment = getIngredientsFragment();

        if (ingredientsFragment != null) {
            ingredientsFragment.setDefaultUnit(unitName);
        }

        mRecipe.setResultUnitId(null);
        mIsRecipeInfoChanged = true;
    }

    public String getResultUnitName() {
        DetailsFragment detailsFragment = getDetailsFragment();
        if (detailsFragment != null) {
            return  detailsFragment.mUnitView.getText().toString();
        }
        return null;
    }


    // TODO: correct it
    @Override
    public void onAddIngredientClick(IngredientInRecipeInfo ingredient) {
        IngredientsFragment ingredientsFragment = getIngredientsFragment();
        if (ingredientsFragment != null) {
            ingredientsFragment.onAddIngredientClick(ingredient);
        }
    }

    @Override
    public void onIngredientDelete(int id) {
        if (mDeletedIngredientsIds == null) {
            mDeletedIngredientsIds = new ArrayList<>();
        }

        mDeletedIngredientsIds.add(id);
    }

    @Override
    public void onSave() {
        final RecipeEntity[] recipe = {null};

        final int finishCode = 1;

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == finishCode) {
                    mViewModel.saveRecipeChanges(recipe[0], mDeletedIngredientsIds, prepareIngredientsData(),
                            mNewIngredientsData, mNewUnitsData);
                    mIsRecipeInfoChanged = false;
                    mIngredientsToSaveInfo = null;
                    mDeletedIngredientsIds = null;
                    mNewIngredientsData = null;
                    mNewUnitsData = null;

                    AppSnackbar.make(findViewById(R.id.fragment), R.string.chenges_saved, Snackbar.LENGTH_SHORT).show();
                }
            }
        };


        if (mIsRecipeInfoChanged) {
            Thread thread = new Thread(() -> {
                prepareRecipeData();
                recipe[0] = mRecipe;
                handler.sendEmptyMessage(finishCode);
            });
            thread.start();
        } else {
            handler.sendEmptyMessage(finishCode);
        }
    }

    @Override
    public void onRecipeNameChanged(String newRecipeName) {
        mRecipe.setName(newRecipeName);
        mIsRecipeInfoChanged = true;
    }

    @Override
    public void onSourceChanged(String newSource) {
        mRecipe.setSource(newSource);
        mIsRecipeInfoChanged = true;
    }
}
