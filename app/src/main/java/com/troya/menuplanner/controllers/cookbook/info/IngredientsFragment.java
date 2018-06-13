package com.troya.menuplanner.controllers.cookbook.info;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.troya.menuplanner.App;
import com.troya.menuplanner.R;
import com.troya.menuplanner.Temp.Main.Fragments.BaseTabFragment;
import com.troya.menuplanner.adapters.list.IngredientsInRecipeListAdapter;
import com.troya.menuplanner.controllers.cookbook.info.dialogs.IngredientInRecipeDialog;
import com.troya.menuplanner.helpers.RecipeInfoTabsCallback;
import com.troya.menuplanner.model.db.entity.RecipeEntity;
import com.troya.menuplanner.model.views.IngredientInRecipeInfo;
import com.troya.menuplanner.viewmodel.IngredientsViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class IngredientsFragment extends BaseTabFragment implements IngredientInRecipeDialog.DialogCallback {
    private static final int LAYOUT = R.layout.fragment_ingredients;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    private RecipeEntity mRecipe;
    private RecipeInfoTabsCallback mCallback;

    public static IngredientsFragment newInstance(Context context, RecipeEntity recipe) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeInfoActivity.KEY_RECIPE, recipe);
        fragment.setArguments(bundle);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_ingredients));

        return fragment;
    }

    private Unbinder mUnbinder;
    private IngredientsViewModel mViewModel;
    private IngredientsInRecipeListAdapter mAdapter;
    private List<IngredientInRecipeInfo> mIngredientsInfo;

    @BindView(R.id.listView)
    RecyclerView mIngredientsListView;
    @BindView(R.id.txtUnit)
    TextView mUnitView;

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mIngredientsListView.setLayoutManager(layoutManager);
        mAdapter = new IngredientsInRecipeListAdapter(this.getContext());
        mIngredientsListView.setAdapter(mAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                mIngredientsListView.getContext(),
                ((LinearLayoutManager) layoutManager).getOrientation());

        itemDecoration.setDrawable(getActivity().getDrawable(R.drawable.divider_white));

        mIngredientsListView.addItemDecoration(itemDecoration);
    }

    @OnClick(R.id.fabAddNew)
    void onAddNewClick() {
        if (this.getContext() != null) {
            new IngredientInRecipeDialog(this.getContext(), null, this).show();
        }
    }


    public void setDefaultUnit(String unit) {
        mUnitView.setText(unit);
    }


    /*------------------------------- Lifecycle -------------------------------*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof RecipeInfoActivity) {
            ((RecipeInfoActivity) context).setIngredientsFragmentTag(getTag());
        }

        try {
            mCallback = (RecipeInfoTabsCallback) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " +
                    RecipeInfoTabsCallback.class.getSimpleName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.mRecipe = bundle.getParcelable(RecipeInfoActivity.KEY_RECIPE);
        }

        View view = inflater.inflate(LAYOUT, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        if (mRecipe.getResultUnitId() != null) {
            mUnitView.setText(mViewModel.getUnitNameById(mRecipe.getResultUnitId()));
        }

        initRecyclerView();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (this.getActivity() != null) {
            ((App) this.getActivity().getApplication())
                    .getAppComponent()
                    .inject(this);

            mViewModel = ViewModelProviders.of(this, mViewModelFactory)
                    .get(IngredientsViewModel.class);
        }

        mViewModel.getIngredientsById(mRecipe.getId()).observe(this, ingredients -> mAdapter.setData(ingredients));
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }



    /*------------------------------- Callbacks -------------------------------*/

    @Override
    public void onVerifiedDismiss(IngredientInRecipeInfo ingredient) {


/*
    this.mIngredientInRecipeEntity = new IngredientInRecipeEntity();
        if (ingredient != null) {
            this.mIngredientInRecipeEntity.setId(ingredient.getId());

    IngredientRepo ingredientRepo = new IngredientRepo(
            this.getContext(),
            new IngredientEntity(mIngredientNameView.getText().toString()),
            null);
    long ingredientId = ingredientRepo.getOrCreateIngredientId();
    @Nullable
    Long unitId = (mUnitView.getText().toString().isEmpty()) ? null
            : new UnitRepo(this.getContext(), new UnitEntity(mUnitView.getText().toString())).getOrCreateUnitId();

                mRecipeIngredient.setRecipeId(mRecipe);
                mRecipeIngredient.setIngredientId(ingredientId);*/
    }
}
