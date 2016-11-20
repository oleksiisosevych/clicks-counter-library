package com.oleksiisosevych.flickrimagesbrowsermvp.data.local;

import android.support.annotation.NonNull;

import com.oleksiisosevych.flickrimagesbrowsermvp.data.ImagesDataSource;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.api.FlickrService;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.flickr.Photo;
import com.oleksiisosevych.flickrimagesbrowsermvp.data.models.flickr.PhotoSearchResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Concrete implementation data source that searches for images using Flickr API
 */
public class FlickrImagesDataSource implements ImagesDataSource {

    private static final String BASE_URL = "https://api.flickr.com/";
    private static FlickrImagesDataSource INSTANCE;
    private FlickrService flickrService;

    // Prevent direct instantiation.
    private FlickrImagesDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        flickrService = retrofit.create(FlickrService.class);
    }

    public static FlickrImagesDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FlickrImagesDataSource();
        }
        return INSTANCE;
    }

    @Override public void getImages(@NonNull String searchQuery,
                                    @NonNull final LoadImagesCallback callback) {
        Call<PhotoSearchResult> getPhotosCall = flickrService.searchPictures(searchQuery);
        getPhotosCall.enqueue(new Callback<PhotoSearchResult>() {
            @Override
            public void onResponse(Call<PhotoSearchResult> call, Response<PhotoSearchResult> response) {
                if (response.isSuccess()) {
                    List<String> imageUrls = new ArrayList<>();
                    for (Photo aPhoto : response.body().getPhotos().getPhoto()) {
                        imageUrls.add(getPhotoUrl(aPhoto));
                    }
                    callback.onImagesLoaded(imageUrls);
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override public void onFailure(Call<PhotoSearchResult> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    private String getPhotoUrl(Photo photo) {
        return String.format("http://farm%s.static.flickr.com/%s/%s_%s.jpg",
                photo.getFarm(),
                photo.getServer(),
                photo.getId(),
                photo.getSecret());
    }
}
