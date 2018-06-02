package com.troya.menuplanner.Temp.Recipes.info;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.troya.menuplanner.R;
import com.troya.menuplanner.adapters.tab.RecipeTabFragmentAdapter;
import com.troya.menuplanner.model.db.entity.RecipeEntity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeInfoFragment extends Fragment {
    private static final int LAYOUT = R.layout.activity_recipe_info;

    // TODO: Rename parameter arguments, choose names that match
    private static final String KEY_RECIPE = "recipe";

 /*   @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.editRecipeName)
    TextView mRecipeNameView;*/


    // TODO: Rename and change types of parameters
    private RecipeEntity mRecipe;
    private Unbinder mUnbinder;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static RecipeInfoFragment newInstance(RecipeEntity recipe) {
        RecipeInfoFragment fragment = new RecipeInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(KEY_RECIPE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container);
        mUnbinder = ButterKnife.bind(this, view);
        RecipeTabFragmentAdapter adapter = new RecipeTabFragmentAdapter(this.getContext(),
                this.getActivity().getSupportFragmentManager(), mRecipe);
        return view;
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
