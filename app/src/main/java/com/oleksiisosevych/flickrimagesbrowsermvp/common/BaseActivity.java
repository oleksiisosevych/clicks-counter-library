package com.oleksiisosevych.flickrimagesbrowsermvp.common;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Common functionality for all activities
 */
public class BaseActivity extends AppCompatActivity {

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
