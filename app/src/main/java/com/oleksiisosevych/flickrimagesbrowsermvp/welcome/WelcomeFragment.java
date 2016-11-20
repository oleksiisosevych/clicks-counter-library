package com.oleksiisosevych.flickrimagesbrowsermvp.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private TextView welcomeMessage;

    private ImageView categoryImage;

    private CardView categoryCard;

    private Button btnAllCategories;

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
        View view = inflater.inflate(R.layout.fr_welcome, container, false);

        // find views
        welcomeMessage = (TextView) view.findViewById(R.id.welcome_message);
        categoryImage = (ImageView) view.findViewById(R.id.category_image);
        btnAllCategories = (Button) view.findViewById(R.id.btn_to_categories);
        categoryCard = (CardView) view.findViewById(R.id.card_view);

        btnAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                presenter.openCategoryList();
            }
        });

        categoryCard.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                presenter.openCategory();
            }
        });

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
                .into(categoryImage);
        welcomeMessage.setText(getString(R.string.welcome_message, category.getName()));
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

