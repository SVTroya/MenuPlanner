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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class IngredientsFragment extends BaseTabFragment
        implements IngredientInRecipeDialog.DialogCallback, IngredientsInRecipeListAdapter.IngredientsListCallback {
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

    @BindView(R.id.listView)
    RecyclerView mIngredientsListView;
    @BindView(R.id.txtUnit)
    TextView mUnitView;

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mIngredientsListView.setLayoutManager(layoutManager);
        mAdapter = new IngredientsInRecipeListAdapter(this.getContext(), this);
        mIngredientsListView.setAdapter(mAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                mIngredientsListView.getContext(),
                ((LinearLayoutManager) layoutManager).getOrientation());

        itemDecoration.setDrawable(getActivity().getDrawable(R.drawable.divider_white));

        mIngredientsListView.addItemDecoration(itemDecoration);
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
        } catch (ClassCastException e) {
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

        mViewModel.getIngredientsById(mRecipe.getId()).observe(this, ingredients -> {
            mAdapter.setData(ingredients);
        });

        if (mRecipe.getResultUnitId() != null) {
            mUnitView.setText(mViewModel.getUnitNameById(mRecipe.getResultUnitId()));
        }
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }


    /*------------------------------- Callbacks -------------------------------*/
    @Override
    public void onVerifiedDismiss(IngredientInRecipeInfo ingredient) {
        if (ingredient.getPosition() != null){
            mAdapter.changeData(ingredient);
        } else {
            mAdapter.addData(ingredient);
        }
        mCallback.addIngredientInfo(ingredient);
    }

    public void onAddIngredientClick(IngredientInRecipeInfo ingredient) {
        if (this.getContext() != null) {
            new IngredientInRecipeDialog(this.getContext(), ingredient, this, mViewModel).show();
        }
    }

    @Override
    public void onDelete(int id) {
        mCallback.onIngredientDelete(id);
    }

    @Override
    public void onEdit(IngredientInRecipeInfo ingredient) {
        onAddIngredientClick(ingredient);
    }


}
