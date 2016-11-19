package com.oleksiisosevych.flickrimagesbrowsermvp.categorydetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.oleksiisosevych.flickrimagesbrowsermvp.R;
import com.oleksiisosevych.flickrimagesbrowsermvp.imagedetails.ImageDetailsActivity;

import java.util.List;

/**
 * Display a grid of images after selecting category.
 */
public class CategoryDetailsFragment extends Fragment implements CategoryDetailsContract.View {
    private static final int GRID_COLUMNS_COUNT = 3;

    private CategoryDetailsContract.Presenter presenter;
    private RecyclerView recyclerView;

    public CategoryDetailsFragment() {
        // Required public constructor
    }

    public static CategoryDetailsFragment newInstance() {
        return new CategoryDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fr_category_details, container, false);

        // find views
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), GRID_COLUMNS_COUNT));
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

    @Override public void setPresenter(CategoryDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void showPictures(List<String> imageUrls) {
        recyclerView.setAdapter(new PicturesAdapter(imageUrls));
    }

    @Override public void showPictureDetails(@NonNull String imageUrl) {
        Intent intent = ImageDetailsActivity.getLaunchIntent(getActivity(), imageUrl);
        startActivity(intent);
    }

    private class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.PictureViewHolder> {
        private List<String> imageUrls;

        public PicturesAdapter(List<String> imageUrls) {
            this.imageUrls = imageUrls;
        }

        @Override
        public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_details_grid_item, parent, false);
            return new PictureViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PictureViewHolder holder, int position) {
            final String imageUrl = imageUrls.get(position);
            Glide.with(getContext()).load(imageUrl).into(holder.pic);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    presenter.openPictureDetails(imageUrl);
                }
            });
        }


        @Override public int getItemCount() {
            return imageUrls.size();
        }

        class PictureViewHolder extends RecyclerView.ViewHolder {
            public ImageView pic;

            public PictureViewHolder(View itemView) {
                super(itemView);
                pic = (ImageView) itemView.findViewById(R.id.picture);
            }
        }
    }
}
