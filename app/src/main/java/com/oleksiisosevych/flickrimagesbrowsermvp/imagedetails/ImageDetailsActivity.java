package com.oleksiisosevych.flickrimagesbrowsermvp.imagedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.oleksiisosevych.flickrimagesbrowsermvp.R;
import com.oleksiisosevych.flickrimagesbrowsermvp.util.ActivityUtils;

public class ImageDetailsActivity extends AppCompatActivity {

    public static final String IMAGE_URL_EXTRA_KEY = "image_url_key";

    public static Intent getLaunchIntent(@NonNull Context from, String imageUrl) {
        Intent intent = new Intent(from, ImageDetailsActivity.class);
        intent.putExtra(IMAGE_URL_EXTRA_KEY, imageUrl);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iamge_details);


        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        // Get the requested task id

        ImageDetailsFragment imageDetailsFragment = (ImageDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (imageDetailsFragment == null) {
            imageDetailsFragment = ImageDetailsFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    imageDetailsFragment, R.id.contentFrame);
        }

        // Create the presenter
        String imageUrl = getIntent().getStringExtra(IMAGE_URL_EXTRA_KEY);
        new ImageDetailsPresenter(
                imageDetailsFragment,
                imageUrl);
    }
}
