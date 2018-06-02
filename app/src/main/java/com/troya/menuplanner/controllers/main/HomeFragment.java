package com.troya.menuplanner.controllers.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.troya.menuplanner.R;
import com.troya.menuplanner.controllers.cookbook.RecipesListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {
    private static final int LAYOUT = R.layout.fragment_home;

    Unbinder mUnbinder;

    @BindView(R.id.cardPlanMenu)
    CardView mPlanMenuCard;

    @BindView(R.id.cardCookbook)
    CardView mCookbookCard;

    @BindView(R.id.cardShoppingCart)
    CardView mShoppingListCard;

    @BindView(R.id.cardStorage)
    CardView mStorageCard;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);

        return fragment;
    }


    /*------------------------------- Lifecycle -------------------------------*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container);
        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }


    /*------------------------------- Callbacks -------------------------------*/

    @OnClick(R.id.cardCookbook)
    void onCookbookClick() {
        Intent intent = new Intent(this.getActivity(), RecipesListActivity.class);
        startActivity(intent);
    }
}
