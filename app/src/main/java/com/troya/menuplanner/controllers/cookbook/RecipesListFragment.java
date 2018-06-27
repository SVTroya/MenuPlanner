package com.troya.menuplanner.controllers.cookbook;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.troya.menuplanner.App;
import com.troya.menuplanner.R;
import com.troya.menuplanner.adapters.list.RecipesListAdapter;
import com.troya.menuplanner.controllers.cookbook.info.RecipeInfoActivity;
import com.troya.menuplanner.controllers.dialogs.AddRecipeDialog;
import com.troya.menuplanner.model.db.entity.CategoryEntity;
import com.troya.menuplanner.model.db.entity.RecipeEntity;
import com.troya.menuplanner.viewmodel.RecipeListViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RecipesListFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_recipes_list;

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    public static RecipesListFragment newInstance() {
        Bundle args = new Bundle();
        RecipesListFragment fragment = new RecipesListFragment();
        fragment.setArguments(args);

        return fragment;
    }
    private RecipeListViewModel mViewModel;
    private RecipesListAdapter mAdapter;
    private Unbinder mUnbinder;

    private LiveData<List<CategoryEntity>> mCategories;

    @BindView(R.id.listGroupedRecipes)
    RecyclerView mRecipesListView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.fabAddNew)
    FloatingActionButton mAddNewButton;

    public void initRecycler() {
        mCategories = mViewModel.getAllCategories();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecipesListView.setLayoutManager(layoutManager);
        mAdapter = new RecipesListAdapter(
                id -> {
                    Intent intent = new Intent(RecipesListFragment.this.getActivity(), RecipeInfoActivity.class);
                    intent.putExtra(RecipeInfoActivity.KEY_RECIPE_ID, id);
                    startActivity(intent);
                });
        mRecipesListView.setAdapter(mAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                mRecipesListView.getContext(),
                layoutManager.getOrientation());

        itemDecoration.setDrawable(getActivity().getDrawable(R.drawable.divider_white));

        mRecipesListView.addItemDecoration(itemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(mRecipesListView);
    }

    /*------------------------------- Lifecycle -------------------------------*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mToolbar.setTitle(R.string.cookbook);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null)
            ((App) getActivity().getApplication())
                    .getAppComponent()
                    .inject(this);

        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(RecipeListViewModel.class);

        // mViewModel.insertDummyData();

        initRecycler();
        mViewModel.getAllRecipes().observe(this, recipes -> mAdapter.setData(recipes));
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }


    /*------------------------------- Callbacks -------------------------------*/

    @OnClick(R.id.fabAddNew)
    void onAddNewClick() {
        if (this.getActivity() != null) {
            AddRecipeDialog dialog = new AddRecipeDialog(this.getActivity(), new AddRecipeDialog.OnPositiveListener() {
                @Override
                public String onPositive(String text) {
                    if (TextUtils.isEmpty(text)) {
                        return getString(R.string.recipe_name_cant_be_empty);
                    } else {
                        return null;
                    }
                }
            });

            dialog.show();

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (((AddRecipeDialog) dialog).isPassed()) {
                        int recipeId = mViewModel.addNewRecipe(new RecipeEntity(((AddRecipeDialog) dialog).getText()));
                        Intent intent = new Intent(RecipesListFragment.this.getActivity(), RecipeInfoActivity.class);
                        intent.putExtra(RecipeInfoActivity.KEY_RECIPE_ID, recipeId);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mViewModel.deleteRecipeById((int)viewHolder.getItemId());
            }
        };

        return itemTouchHelperCallback;
    }
}
