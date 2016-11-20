# Category click counter library

Android app that leverages the [Flickr API](https://www.flickr.com/services/api/) to search for and display images from handul set of categories. It also tracks how many times user selected each category and adjust appearence of category list according to previous user activity.

The app is composed of 4 screens. Welcome screen is shown to user in case user already used the app and selected some category at least once, on the welcome screen the most popular (or the one with smaller id in case of tie) category. It is possible to navigate to most used Category images screen or to Category list screen from here. Category list screen displays a grid of hardcoded image categories. After a user selects a catogory from the list, a Category Details screen appears displaying 12 first search results from Flickr. When user taps on image it is presented in the Image Details screen with big selected image.

**Welcome Screen**

![Imgur](https://s14.postimg.org/cdtsaonsx/Screen_Shot_2016_11_21_at_00_28_20.png)

**Categories List Screen**

![Imgur](https://s14.postimg.org/q8wo6w7lt/Screen_Shot_2016_11_21_at_00_28_29.png)

**Category Details Screen**

![Imgur](https://s14.postimg.org/ysg24nfy9/Screen_Shot_2016_11_21_at_00_28_39.png)

**Image Details Screen**

![Imgur](https://s14.postimg.org/nhdega935/Screen_Shot_2016_11_21_at_00_28_46.png)

## Overview

The app does the following:

1. Tracks user behaviour and ajusts information presented to user according to previously gathered statistics
2. The tracking module implemented as library module and tracks user events in a local SQLite Database
3. Searchs a list of images given name of category using the [Flickr Api](https://www.flickr.com/services/api/)

## Libraries

This app leverages two third-party libraries:

 * [Square Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java
 * [Glide](https://github.com/bumptech/glide) - Fast and efficient open source media management and image loading framework
