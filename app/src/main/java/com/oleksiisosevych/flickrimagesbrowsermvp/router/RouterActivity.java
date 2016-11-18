package com.oleksiisosevych.flickrimagesbrowsermvp.router;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Starting point for our application that will decide what first activity show to user
 */
public class RouterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean firstTimeUser = true;

        if (firstTimeUser) {
            //show category list
        } else {
            //show welcome back screen
        }

    }
}
