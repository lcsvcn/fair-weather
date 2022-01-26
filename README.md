# Fair Weather App

### Library That I used:

- Retrofit (https://square.github.io/retrofit/) - Robust and simple HTTP client to comunicate with MetaWeather Api
- Moshi (https://github.com/square/moshi) - Alternative to Gson, with way more actively support and otimization for Kotlin from Square Team
- Coroutines (https://github.com/Kotlin/kotlinx.coroutines) - Kotlin Async library
- Timber (https://github.com/JakeWharton/timber) - Alternative to Log, a more secure way to log information
- Okhttp3 (https://square.github.io/okhttp/) - To intercept retrofit and log information about the API calls
- Databinding (https://developer.android.com/jetpack/androidx/releases/databinding) - To bind interface components into activity


### What Library that I would add if this app would be a long time support app:

- Dagger (https://dagger.dev/) - Robust and simple dependency injection to integrate classes and modules within the project

I did not add since it would require to much time to configure it.

Altought the current project that I am currently working has Dagger injection with Repository, Navigation and Custom Components. 
And I am pretty used to it (In large projects it help a lot)

#### Note:

The arch (from ARCHiteture) folder contain many useful configuration files that I use between my projects. Such as ViewExt, StringExt, States and other classes.

The remaining architeture is pretty much commom MVVM without the repository, that I intentionally did not add because of the given time.

## How to run the project:

To run the project, clone the repository and sync gradle. Then run it on latest android studio version.

If you ran into any issues, try to change the jdk compiler to:
    JDK 11 (OpenJDK Runtime Environment Corretto-11.0.13.8.1) 
and the android version to:
    Android Studio Version 2020.3.1 Patch 4


### About the development of this little project

A little bit about my choices into this project. After taking a look into the Api documentation I decided to create a weather class contain all information from these endpoints and reuse this class to create an adapter list. I took a some time to setup the retrofit and opted for Moshi, since it is the most modern approach this days. After that I created an alert container with one ImageView plus 2 TextViews to hold the title and description to show a empty search state and also present errors message.

Then I created the View Model and added Live Data for empty state, error state, list state and scroll state to create an update my activity with real data from the View Model. The scroll state was just to help users to reach to the top of the search easily.

For the design, I opted for the coordinator layout since it would allow me to make the search bar hide when user scrolls into the recycler view. And would not affect the alert component design and loading too.

For the base colors of the app, I did not worry too much and kept the purple design that came with the example app, for contrast I added a lavander colour, that is like a light purple, very light indeed.

About my thoughts about the project, I find the UI and UX that I was able to achive very pleasant. I find no time to add dagger dependency injection and improve repository and retrofit configuration. That was something that would not be in a production app, since I am aware that the code I made makes the retrofit very dependent of the WeatherListActivity, but since it would required more time I kept that way.

Another thing that I did not address in this version was that, if for any reason the users closes the app in the middle of the courotine that fetches the MetaWeatherpi, we should cancel it to avoid memory leaks.

The tests, I created only the basic unit test, to test the live data comportament. I was thinkg about creating some integration test to test the api and the retrofit. But I left just the basic tests, since it would required me to add a lot of boilerplate to both ViewModel and the ViewModelTest in other to make the proper testing that I used daily in my projects.
