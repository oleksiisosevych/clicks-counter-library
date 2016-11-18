package com.oleksiisosevych.flickrimagesbrowsermvp.data.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oleksiisosevych.flickrimagesbrowsermvp.R;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.CategoriesDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.Category;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation data source that gets its data from json file
 * from assets folder
 */
public class CategoriesLocalDataSource implements CategoriesDataSource {
    private static CategoriesLocalDataSource INSTANCE;
    private final Context context;
    private Gson gson;


    // Prevent direct instantiation.
    private CategoriesLocalDataSource(@NonNull Context context) {
        this.context = context;
        gson = new Gson();
    }

    public static CategoriesLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CategoriesLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getCategories(@NonNull LoadCategoriesCallback callback) {
        List<Category> fetchedCategories = loadJSONFromAsset(R.raw.categories);
        if (fetchedCategories == null || fetchedCategories.size() <= 0) {
            callback.onDataNotAvailable();
        } else {
            callback.onCategoriesLoaded(fetchedCategories);
        }
    }

    /**
     * Gets categories list from raw resource file.
     *
     * @return List of fetched categories from file.
     */
    private List<Category> loadJSONFromAsset(@RawRes int resId) {
        String json;
        try {
            InputStream is = context.getResources().openRawResource(resId);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        Type listType = new TypeToken<ArrayList<Category>>() {
        }.getType();
        return gson.fromJson(json, listType);

    }
}
