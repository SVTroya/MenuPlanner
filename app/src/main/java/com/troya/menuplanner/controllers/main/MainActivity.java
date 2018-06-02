package com.troya.menuplanner.controllers.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.troya.menuplanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final int LAYOUT = R.layout.activity_main;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    TextView mTxtUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        ButterKnife.bind(this);

        initToolbar();
        initNavigationView();
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        mToolbar.inflateMenu(R.menu.menu);
    }

    private void initNavigationView() {
        mTxtUserName = mNavigationView.getHeaderView(0).findViewById(R.id.txtUser);
    }
}
