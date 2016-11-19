package com.oleksiisosevych.flickrimagesbrowsermvp.imagedetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oleksiisosevych.flickrimagesbrowsermvp.R;

/**
 * Display big image after selecting it from images grid.
 */
public class ImageDetailsFragment extends Fragment implements ImageDetailsContract.View {

    private ImageDetailsContract.Presenter presenter;

    private TextView textView;

    private ImageView imageView;

    public ImageDetailsFragment() {
        // Required public constructor
    }

    public static ImageDetailsFragment newInstance() {
        return new ImageDetailsFragment();
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

    @Override public void setPresenter(ImageDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override public void showPicture(String imageUrls) {
        Glide.with(getActivity()).load(imageUrls).into(imageView);
    }

}

