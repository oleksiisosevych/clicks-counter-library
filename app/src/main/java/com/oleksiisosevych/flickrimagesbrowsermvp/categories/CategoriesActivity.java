package com.oleksiisosevych.flickrimagesbrowsermvp.categories;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.oleksiisosevych.flickrimagesbrowsermvp.R;
import com.oleksiisosevych.flickrimagesbrowsermvp.common.BaseActivity;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.local.CategoriesLocalDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.local.StatsLibraryDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.util.ActivityUtils;


public class CategoriesActivity extends BaseActivity {


    public static final String BACK_NAVIGATION_KEY = "back_navigation_key";

    public static Intent getLaunchIntent(@NonNull Context from, boolean enableBackNavigation) {
        Intent intent = new Intent(from, CategoriesActivity.class);
        intent.putExtra(BACK_NAVIGATION_KEY, enableBackNavigation);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        boolean enableBackNavigation = getIntent().getBooleanExtra(BACK_NAVIGATION_KEY, false);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(enableBackNavigation);
        ab.setDisplayShowHomeEnabled(enableBackNavigation);

        // Get the requested task id

        CategoriesFragment categoriesFragment = (CategoriesFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (categoriesFragment == null) {
            categoriesFragment = CategoriesFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    categoriesFragment, R.id.contentFrame);
        }

        // Create the presenter
        new CategoriesPresenter(
                StatsLibraryDataSource.getInstance(getApplicationContext()),
                CategoriesLocalDataSource.getInstance(getApplicationContext()),
                categoriesFragment);
    }
}
