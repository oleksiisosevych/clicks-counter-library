package com.oleksiisosevych.flickrimagesbrowsermvp.categories;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.oleksiisosevych.flickrimagesbrowsermvp.R;
import com.oleksiisosevych.flickrimagesbrowsermvp.categorydetails.CategoryDetailsActivity;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Display a grid of {@link Category}s.
 */
public class CategoriesFragment extends Fragment implements CategoriesContract.View {

    private CategoriesContract.Presenter presenter;
    //    private GridLayout gridLayout;
    private RecyclerView recyclerView;

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
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//        gridLayout = (GridLayout) view.findViewById(R.id.category_grid);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        recyclerView.setAdapter(new CategoriesAdapter(categoryList));
    }

    @Override public void showCategoryDetailsUi(@NonNull Category category) {
        Intent intent = CategoryDetailsActivity.getLaunchIntent(getActivity(), category);
        startActivity(intent);
    }

    @Override public void setPresenter(CategoriesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesRowViewHolder> {
        private List<Category> categories;
        private Map<Category, Integer> categoryWeights;
        private Map<Integer, List<Category>> categoriesForRow;
        private boolean allEquals;


        public CategoriesAdapter(List<Category> categoryList) {
            categories = categoryList;
            Collections.sort(categories);
            categoriesForRow = new HashMap<>();
            calculateWeights();
        }

        private void calculateWeights() {
            categoryWeights = new HashMap<>();
            int rowIndex = 0;
            int weightSum = 0;
            List<Category> row = new ArrayList<>();

            allEquals = categories.get(0).getClicksCount() == 0;
            if (!allEquals) {
                if (categories.size() > 1) {
                    categoryWeights.put(categories.get(0), 3);
                    row.add(categories.get(0));
                    categoriesForRow.put(rowIndex++, row);
                }

                if (categories.size() > 2) {
                    categoryWeights.put(categories.get(1), 2);
                    row = new ArrayList<>();
                    row.add(categories.get(1));
                    weightSum += 2;
                }
                allEquals = false;
            }

            for (Category category : categories) {
                if (!categoryWeights.containsKey(category)) {
                    categoryWeights.put(category, 1);
                    row.add(category);
                    weightSum++;
                    if (weightSum >= 3) {
                        categoriesForRow.put(rowIndex++, row);
                        row = new ArrayList<>();
                        weightSum = 0;
                    }
                }
            }
        }

        @Override
        public CategoriesRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_row_item, parent, false);
            return new CategoriesRowViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CategoriesRowViewHolder holder, int position) {
            holder.imagesContainer.removeAllViews();
            List<Category> categoriesInRow = categoriesForRow.get(position);
            for (Category aCategory : categoriesInRow) {
                View view = getViewForCategory(aCategory, holder.imagesContainer);
                view.setLayoutParams(new LinearLayout.LayoutParams(0, (int) getContext().getResources().getDimension(R.dimen.categories_row_height), categoryWeights.get(aCategory)));
                holder.imagesContainer.addView(view);
            }
        }

        private View getViewForCategory(final Category category, ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_grid_item, parent, false);
            ImageView categoryImage = (ImageView) view.findViewById(R.id.picture);
            Glide.with(parent.getContext()).load(category.getImageUrl()).into(categoryImage);
            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    presenter.openCategoryDetails(category);
                }
            });
            return view;
        }

        @Override public int getItemCount() {
            int rowCount = categories.size() / 3 + ((categories.size() % 3 == 0) ? 0 : 1);
            if (allEquals || categories.size() == 0) {
                return rowCount;
            }
            return rowCount + 1;
        }

        public class CategoriesRowViewHolder extends RecyclerView.ViewHolder {
            public LinearLayout imagesContainer;

            public CategoriesRowViewHolder(View itemView) {
                super(itemView);
                imagesContainer = (LinearLayout) itemView.findViewById(R.id.categories_container);

            }
        }
    }
}
