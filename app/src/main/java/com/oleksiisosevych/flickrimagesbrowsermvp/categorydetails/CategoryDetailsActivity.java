package com.oleksiisosevych.flickrimagesbrowsermvp.categorydetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.oleksiisosevych.flickrimagesbrowsermvp.R;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.Category;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.local.ImagesStubDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.util.ActivityUtils;

public class CategoryDetailsActivity extends AppCompatActivity {

    public static final String CATEGORY_EXTRA_KEY = "category_extra_key";

    public static Intent getLaunchIntent(@NonNull Context from, Category category) {
        Intent intent = new Intent(from, CategoryDetailsActivity.class);
        intent.putExtra(CATEGORY_EXTRA_KEY, category);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);


        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        // Get the requested task id

        CategoryDetailsFragment categoryDetailsFragment = (CategoryDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (categoryDetailsFragment == null) {
            categoryDetailsFragment = CategoryDetailsFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    categoryDetailsFragment, R.id.contentFrame);
        }

        // Create the presenter
        Category category = getIntent().getParcelableExtra(CATEGORY_EXTRA_KEY);
        new CategoryDetailsPresenter(
                ImagesStubDataSource.getInstance(),
                categoryDetailsFragment,
                category);
    }
}
