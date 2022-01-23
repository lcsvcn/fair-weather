# Faire Weather App

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
