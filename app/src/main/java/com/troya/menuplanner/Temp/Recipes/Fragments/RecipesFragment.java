package com.troya.menuplanner.Temp.Recipes.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.troya.menuplanner.R;
import com.troya.menuplanner.Temp.Main.Fragments.BaseTabFragment;

public class RecipesFragment extends BaseTabFragment {

    private static final int LAYOUT = R.layout.fragment_recipes_list;
/*
    public static final int LOADER_ID = -1;

    @BindView(R.id.listGroupedRecipes)
    ExpandableListView mRecipesList;

    private RecipesListAdapter mAdapter;

    public static RecipesFragment newInstance(Context context) {
        Bundle args = new Bundle();
        RecipesFragment fragment = new RecipesFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(fragment.getContext().getString(R.string.tab_item_recipes));

        return fragment;
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(LAYOUT, container, false);

        // Lists Adapters init
        /*mAdapter = new RecipesListAdapter(this.getContext(), loaderManager, null);
        mRecipesList.setAdapter(mAdapter);*/

        /*mAddRecipeButton.setOnClickListener(this);
        mAddCookbookButton.setOnClickListener(this);*/

        /*mRecipesList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {

                Intent intent = new Intent(RecipesFragment.this.getContext(), RecipeInfoActivity.class);
                Bundle bundle = new Bundle();
                //bundle.putLong(RecipeRepo.KEY_SELECTED_RECIPE_ID, id);

                intent.putExtras(bundle);
                startActivity(intent);

                return true;
            }
        });*/

        return view;
    }
/*
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddRecipe:
               *//* AddItemWithGroupDialog itemDialog = new AddItemWithGroupDialog(this.getActivity(), null, null);
                itemDialog.show();
                itemDialog.getEditItemName().setHint(R.string.enter_recipe_name);
                itemDialog.getEditGroupName().setHint(R.string.and_you_can_choose_a_cookbook);
                itemDialog.getEditItemName().setHintTextColor(getResources().getColor(R.color.textColorHint));
                ArrayAdapter<String> hintCookbook = new ArrayAdapter<>(this.getContext(),
                        AddItemWithGroupDialog.LAYOUT_HINT_ITEM, CookbookRepo.getAllNames(this.getContext()));
                itemDialog.getEditGroupName().setAdapter(hintCookbook);
                itemDialog.getEditGroupName().setThreshold(1);*//*

               *//* AddDialogOnClickListener listener = new AddDialogOnClickListener(
                        itemDialog,
                        new RecipeRepo(
                                itemDialog.getContext(),
                                null));

                itemDialog.getBtnOk().setOnClickListener(listener);
                itemDialog.getBtnClose().setOnClickListener(listener);*//*
                break;
            case R.id.btnAddBook:
               *//* AddRecipeDialog groupDialog = new AddRecipeDialog(this.getContext(), new CookbookRepo(
                        getContext(),
                        null),
                        new AddRecipeDialog.OnPositiveListener() {
                            @Override
                            public void OnPositive() {

                            }
                        });
                groupDialog.show();
                groupDialog.getEditItemName().setHintTextColor(getResources().getColor(R.color.textColorHint));
                groupDialog.getEditItemName().setHint(R.string.enter_cookbook_name);*//*

                *//*AddDialogOnClickListener listenerGroup = new AddDialogOnClickListener(
                        groupDialog,
                        new CookbookRepo(
                                groupDialog.getContext(),
                                null));

                groupDialog.getBtnOk().setOnClickListener(listenerGroup);
                groupDialog.getBtnClose().setOnClickListener(listenerGroup);*//*
                break;
        }
    }*/
}
