package com.oleksiisosevych.flickrimagesbrowsermvp.data.local;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.oleksiisosevych.flickrimagesbrowsermvp.data.CategoriesDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Integration test for the {@link CategoriesLocalDataSource}, that loads hardcoded category list
 * from resource file.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CategoriesLocalDataSourceTest {
    private final static int CATEGORIES_COUNT = 6;

    private CategoriesLocalDataSource mLocalDataSource;

    @Before
    public void setup() {
        mLocalDataSource = CategoriesLocalDataSource.getInstance(
                InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void testPreConditions() {
        assertNotNull(mLocalDataSource);
    }

    @Test
    public void getCategories() {
        // Initialize mock for the callback.
        CategoriesDataSource.LoadCategoriesCallback callback = mock(CategoriesDataSource.LoadCategoriesCallback.class);

        // When request all categories from local data source
        mLocalDataSource.getCategories(callback);

        // Then CATEGORIES_COUNT tasks are parsed and returned
        ArgumentCaptor<List> categoriesListCaptor = ArgumentCaptor.forClass(List.class);
        verify(callback).onCategoriesLoaded(categoriesListCaptor.capture());
        assertTrue(categoriesListCaptor.getValue().size() == CATEGORIES_COUNT);
    }
}