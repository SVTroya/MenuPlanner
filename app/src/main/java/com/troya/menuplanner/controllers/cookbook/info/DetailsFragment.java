package com.troya.menuplanner.controllers.cookbook.info;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.troya.menuplanner.R;
import com.troya.menuplanner.Temp.Main.Fragments.BaseTabFragment;
import com.troya.menuplanner.helpers.FloatStringHelper;
import com.troya.menuplanner.helpers.RecipeInfoTabsCallback;
import com.troya.menuplanner.model.db.entity.RecipeEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

public class DetailsFragment extends BaseTabFragment {
    private static final int LAYOUT = R.layout.fragment_details;

    private RecipeEntity mRecipe;
    private RecipeInfoTabsCallback mCallback;

    public static DetailsFragment newInstance(Context context, RecipeEntity recipe) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeInfoActivity.KEY_RECIPE, recipe);
        fragment.setArguments(bundle);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_details));

        return fragment;
    }

    private Unbinder mUnbinder;

    @BindView(R.id.editResult)
    EditText mResultAmountView;
    @BindView(R.id.editUnit)
    AutoCompleteTextView mUnitView;

    /*------------------------------- Lifecycle -------------------------------*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof RecipeInfoActivity) {
            ((RecipeInfoActivity) context).setDetailsFragmentTag(getTag());
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
            mRecipe = bundle.getParcelable(RecipeInfoActivity.KEY_RECIPE);
        }

        mView = inflater.inflate(LAYOUT, container, false);
        mUnbinder = ButterKnife.bind(this, mView);

        return mView;
    }

    private void setup() {
        if (mRecipe != null) {
            mResultAmountView.setText(FloatStringHelper.getCorrectedValue(mRecipe.getResultAmount()));
            //mUnitView.setText(mRecipe.);
        }
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    /*------------------------------- Callbacks -------------------------------*/

    @OnTextChanged(R.id.editUnit)
    void onUnitChanged() {
        mCallback.setResultUnit(mUnitView.getText().toString());
    }
}
