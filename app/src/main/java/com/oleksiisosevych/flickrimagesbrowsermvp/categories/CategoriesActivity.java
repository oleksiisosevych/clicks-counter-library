package com.oleksiisosevych.flickrimagesbrowsermvp.categories;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.oleksiisosevych.flickrimagesbrowsermvp.R;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.local.CategoriesLocalDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.util.ActivityUtils;


public class CategoriesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

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
                CategoriesLocalDataSource.getInstance(getApplicationContext()),
                categoriesFragment);
    }
}
