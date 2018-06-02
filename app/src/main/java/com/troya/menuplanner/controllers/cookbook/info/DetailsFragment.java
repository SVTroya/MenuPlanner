package com.troya.menuplanner.controllers.cookbook.info;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.troya.menuplanner.R;
import com.troya.menuplanner.Temp.Main.Fragments.BaseTabFragment;

import java.util.Locale;

public class DetailsFragment extends BaseTabFragment {
    private static final int LAYOUT = R.layout.fragment_details;

    public static DetailsFragment newInstance(Context context, Bundle bundle) {
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(bundle);
        fragment.setContext(context);
        fragment.setTitle(fragment.getContext().getString(R.string.tab_item_details));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();

        mView = inflater.inflate(LAYOUT, container, false);

        if (bundle != null) {
            String keyResultAmount = "resultAmount";
            if (bundle.containsKey(keyResultAmount)) {
                Float result = bundle.getFloat(keyResultAmount);
                ((TextView)mView.findViewById(R.id.editResult)).setText(String.format(Locale.getDefault(), "%.0f", result));
            }

            ((TextView)mView.findViewById(R.id.editRecipeText)).setText(bundle.getString("comment"));

        }

        return mView;
    }
}
