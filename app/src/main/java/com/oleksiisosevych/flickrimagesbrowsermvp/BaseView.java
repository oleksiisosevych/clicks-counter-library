package com.oleksiisosevych.flickrimagesbrowsermvp;

/**
 * Base class for all view interfaces
 */
public interface BaseView<T> {

    void setPresenter(T presenter);

}
