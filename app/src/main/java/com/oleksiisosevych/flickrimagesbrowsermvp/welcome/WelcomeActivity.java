package com.oleksiisosevych.flickrimagesbrowsermvp.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.oleksiisosevych.flickrimagesbrowsermvp.R;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.local.CategoriesLocalDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.local.DummyStatisticsDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.util.ActivityUtils;

public class WelcomeActivity extends AppCompatActivity {

    public static Intent getLaunchIntent(@NonNull Context from) {
        Intent intent = new Intent(from, WelcomeActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);


        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setDisplayShowHomeEnabled(false);

        // Get the requested task id

        WelcomeFragment welcomeFragment = (WelcomeFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (welcomeFragment == null) {
            welcomeFragment = WelcomeFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    welcomeFragment, R.id.contentFrame);
        }

        new WelcomePresenter(
                CategoriesLocalDataSource.getInstance(this),
                DummyStatisticsDataSource.getInstance(),
                welcomeFragment);
    }
}
