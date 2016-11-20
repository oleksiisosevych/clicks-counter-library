package com.oleksiisosevych.flickrimagesbrowsermvp.welcome;

import com.google.common.collect.Lists;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.CategoriesDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.StatisticsDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.Category;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.EventStat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the implementation of {@link WelcomePresenter}
 */
public class WelcomePresenterTest {
    private static List<Category> CATEGORIES;
    private static List<EventStat> EVENT_STATS;

    @Mock
    CategoriesDataSource mockCategoriesDataSource;
    @Mock
    StatisticsDataSource mockStatisticsDataSource;
    @Mock
    WelcomeContract.View mockView;

    @Captor
    private ArgumentCaptor<CategoriesDataSource.LoadCategoriesCallback> loadCategoriesCallbackCaptor;

    @Captor
    private ArgumentCaptor<StatisticsDataSource.LoadEventStatisticsCallback> loadEventStatisticsCallbackCaptor;

    private WelcomePresenter welcomePresenter;


    @Before
    public void setUp() throws Exception {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        welcomePresenter = new WelcomePresenter(mockCategoriesDataSource,
                mockStatisticsDataSource, mockView);

        // Make some test categories
        CATEGORIES = Lists.newArrayList(new Category("Title1"),
                new Category("Title2"), new Category("Title3"));

        // Make some test stats
        EVENT_STATS = Lists.newArrayList(new EventStat("Title1", 1),
                new EventStat("Title2", 2), new EventStat("Title3", 3));

    }


    @Test
    public void loadAllCategoriesStatisticsAndLoadMostPopularIntoView() {
        // Given an initialized Welcome presenter
        // When loading of categories is requested
        welcomePresenter.start();

        // Callback is captured and invoked with stubbed tasks
        verify(mockCategoriesDataSource).getCategories(loadCategoriesCallbackCaptor.capture());
        loadCategoriesCallbackCaptor.getValue().onCategoriesLoaded(CATEGORIES);

        verify(mockStatisticsDataSource).getAllEventsStats(loadEventStatisticsCallbackCaptor.capture());
        loadEventStatisticsCallbackCaptor.getValue().onStatsLoaded(EVENT_STATS);

        // Then progress indicator is shown
        InOrder inOrder = inOrder(mockView);
        ArgumentCaptor<Category> showHottestCategoryCaptor = ArgumentCaptor.forClass(Category.class);
        inOrder.verify(mockView).showHottestCategory(showHottestCategoryCaptor.capture());
        assertTrue(showHottestCategoryCaptor.getValue().getId().equals("Title3"));
    }

    @Test
    public void clickOnCategory() {
        // When click on category image
        welcomePresenter.openCategory();

        // Then show Category details
        verify(mockView).navigateToCategoryDetails(any(Category.class));
    }

    @Test
    public void clickOnShowAllCategories() {
        // When click on show all categories button
        welcomePresenter.openCategoryList();

        // Then show Category list
        verify(mockView).navigateToCategoryList();
    }

}