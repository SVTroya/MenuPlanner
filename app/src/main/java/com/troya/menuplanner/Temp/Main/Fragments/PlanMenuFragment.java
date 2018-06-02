package com.troya.menuplanner.Temp.Main.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.troya.menuplanner.R;

public class PlanMenuFragment extends BaseTabFragment {
    private static final int LAYOUT = R.layout.fragment_plan_menu;

    public static PlanMenuFragment newInstance(Context context) {
        Bundle args = new Bundle();
        PlanMenuFragment fragment = new PlanMenuFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle("");

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(LAYOUT,container,false);
    }
}
