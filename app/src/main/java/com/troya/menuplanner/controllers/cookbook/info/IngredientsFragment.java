package com.troya.menuplanner.controllers.cookbook.info;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.troya.menuplanner.adapters.list.RecipeIngredientsListAdapter;
import com.troya.menuplanner.R;
import com.troya.menuplanner.Temp.Main.Fragments.BaseTabFragment;

public class IngredientsFragment extends BaseTabFragment {
    public static final String TAG = IngredientsFragment.class.getSimpleName();
    private static final int LAYOUT = R.layout.fragment_ingredients;

    private Long mRecipeId = (long)-1;

    public static IngredientsFragment newInstance(Context context, Bundle bundle) {
        IngredientsFragment fragment = new IngredientsFragment();
        fragment.setArguments(bundle);
        fragment.setContext(context);
        fragment.setTitle(fragment.getContext().getString(R.string.tab_item_ingredients));

        return fragment;
    }


    private RecipeIngredientsListAdapter mAdapter;


    /*------------------------------- Lifecycle -------------------------------*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /*Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.mRecipeId = bundle.getLong(RecipeRepo.KEY_SELECTED_RECIPE_ID);
        }*/

        mView = inflater.inflate(LAYOUT,container,false);

        RecyclerView ingredientsRecyclerView = mView.findViewById(R.id.listView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ingredientsRecyclerView.setLayoutManager(layoutManager);

        /*FloatingActionButton btnAdd = mView.findViewById(R.id.fbtnAdd);
        btnAdd.setOnClickListener(new View.Callback() {
            @Override
            public void onClick(View view) {
                new RecipeIngredientDialog(IngredientsFragment.this.getContext(), mRecipeId, null).show();
            }
        });

        mAdapter = new RecipeIngredientsListAdapter(this.getContext(), null, mRecipeId);
        mAdapter.notifyDataSetChanged();

        ingredientsRecyclerView.setAdapter(mAdapter);*/

        return mView;
    }
}
