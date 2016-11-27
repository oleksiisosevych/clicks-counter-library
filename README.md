# Category click counter library

Android test project that uses [Flickr API](https://www.flickr.com/services/api/) to search and display images from given set of categories. Main objective lies in implementing tracking library module for tracking how many times user selected each category and adjust appearence of category list along with workflow according to previously trackeddata.

The app is composed of 4 screens. Welcome screen is shown to user in case user already used the app and selected some category at least once, on the welcome screen the most popular (or the one with smaller id in case of tie) category. It is possible to navigate to most used Category images screen or to Category list screen from here. Category list screen displays a grid of hardcoded image categories. After a user selects a catogory from the list, a Category Details screen appears displaying 12 first search results from Flickr. When user taps on image it is presented in the Image Details screen with big selected image.

## Overview

The app does following:

1. Tracks user behaviour and ajusts information presented to user according to previously gathered statistics
2. Tracking module implemented as library module and tracks user events in a local SQLite Database
3. Searchs a list of images given name of category using the [Flickr Api](https://www.flickr.com/services/api/)
4. Depending on previously tracked user event App displays wellcome screen or categies screen with adjusted appearence

## Screen descriptions

**First Screen**

|Non-first time users:                           | First time users:                             |
|:----------------------------------------------:|:----------------------------------------------|
|<img src="https://goo.gl/1b9YFn" height="350">  |<img src="https://goo.gl/xGctrt" height="350"> |

* First-time user is determined by existance of category selection event clicks in the databse
* If user launch the app previously, but didn't navigate to any category it is considered to be the first-time user
* In case user clears stored data from setting menu all previously statistics gots viped out and therefore treated as First-timer

**Categories List Screen**

<img src="https://goo.gl/B455ek" height="350">

* Shown after user taps on "See all categories" from Welcome back screen
* Shows the most popular category on top with size 3x1, second most popular category with size 2x1, other cateogries have  1x1 size
* To determine popularity sorting is performed first on clicks count stats parameter and in case of tie - on id field of category.

**Category Details Screen**

<img src="https://s17.postimg.org/tktwyghwv/Screenshot_1480275269.png" height="350">

* Display grid of images from previously selected category retrieved from Flickr API

**Image Details Screen**

<img src="https://s14.postimg.org/nhdega935/Screen_Shot_2016_11_21_at_00_28_46.png" height="350">

* Display big image after selecting it from Category Details Screen

## Libraries

This app leverages two third-party libraries:

 * [Square Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java
 * [Glide](https://github.com/bumptech/glide) - Fast and efficient open source media management and image loading framework
