package com.oleksiisosevych.flickrimagesbrowsermvp.categories;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oleksiisosevych.flickrimagesbrowsermvp.R;
import com.oleksiisosevych.flickrimagesbrowsermvp.categorydetails.CategoryDetailsActivity;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.Category;

import java.util.List;

/**
 * Display a grid of {@link com.oleksiisosevych.flickrimagesbrowsermvp.data.Category}s.
 */
public class CategoriesFragment extends Fragment implements CategoriesContract.View {

    private CategoriesContract.Presenter presenter;
    private GridLayout gridLayout;

    public CategoriesFragment() {
    }


    public static CategoriesFragment newInstance() {
        return new CategoriesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fr_categories, container, false);

        gridLayout = (GridLayout) view.findViewById(R.id.category_grid);
        return view;
    }

    @Override public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override public void showCategoriesList(List<Category> categoryList) {
        gridLayout.removeAllViews();
        int index = 1;
        for (final Category category : categoryList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.categories_grid_item, gridLayout, false);
            ((GridLayout.LayoutParams) view.getLayoutParams()).columnSpec =
                    GridLayout.spec(GridLayout.UNDEFINED, (index++ % 3 > 0) ? 2f : 1f);

            ImageView picture = (ImageView) view.findViewById(R.id.picture);
            TextView title = (TextView) view.findViewById(R.id.title);

            Glide.with(view.getContext())
                    .load(category.getImageUrl())
                    .dontAnimate()
                    .into(picture);
            title.setText(category.getName());

            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    presenter.openCategoryDetails(category);
                }
            });

            gridLayout.addView(view);
        }
    }

    @Override public void showCategoryDetailsUi(@NonNull Category category) {
        Intent intent = CategoryDetailsActivity.getLaunchIntent(getActivity(), category);
        startActivity(intent);
    }

    @Override public void setPresenter(CategoriesContract.Presenter presenter) {
        this.presenter = presenter;
    }

}
