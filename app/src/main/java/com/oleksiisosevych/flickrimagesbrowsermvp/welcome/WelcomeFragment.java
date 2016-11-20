package com.oleksiisosevych.flickrimagesbrowsermvp.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oleksiisosevych.flickrimagesbrowsermvp.R;
import com.oleksiisosevych.flickrimagesbrowsermvp.categories.CategoriesActivity;
import com.oleksiisosevych.flickrimagesbrowsermvp.categorydetails.CategoryDetailsActivity;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.Category;

/**
 * Display big image after selecting it from images grid.
 */
public class WelcomeFragment extends Fragment implements WelcomeContract.View {

    private WelcomeContract.Presenter presenter;

    private TextView textView;

    private ImageView imageView;

    public WelcomeFragment() {
        // Required public constructor
    }

    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fr_image_details, container, false);

        // find views
        textView = (TextView) view.findViewById(R.id.text_view);
        imageView = (ImageView) view.findViewById(R.id.image_view);

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

    @Override public void setPresenter(WelcomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void showHottestCategory(@NonNull Category category) {
        Glide.with(getActivity())
                .load(category.getImageUrl())
                .into(imageView);
    }

    @Override public void navigateToCategoryDetails(@NonNull Category category) {
        Intent intent = CategoryDetailsActivity.getLaunchIntent(getActivity(), category);
        startActivity(intent);
    }

    @Override public void navigateToCategoryList() {
        Intent intent = CategoriesActivity.getLaunchIntent(getActivity());
        startActivity(intent);
    }
}

