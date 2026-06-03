# Movie App

Android native application to discover movies using [TMDB API](https://api.themoviedb.org).

## Features

- Browse official movie genres
- Discover movies by genre with endless scrolling
- View movie details (overview, rating, runtime, genres)
- Watch YouTube trailers
- Read user reviews with endless scrolling
- Full error and empty state handling

## Tech Stack

| Category | Library |
|----------|---------|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Architecture | Clean Architecture + MVVM |
| DI | Hilt |
| Networking | Retrofit + OkHttp |
| Serialization | Kotlin Serialization |
| Pagination | Paging 3 |
| Image Loading | Coil |
| Async | Coroutines + Flow |
| Navigation | Navigation Compose |

## Architecture

```
app/
├── data/
│   ├── remote/
│   │   ├── api/          # Retrofit interface + PagingSources
│   │   ├── dto/          # API response models
│   │   └── interceptor/  # Auth (API key) interceptor
│   ├── repository/       # Repository implementation
│   └── mapper/           # DTO → Domain mappers
├── domain/
│   ├── model/            # Pure Kotlin entities
│   ├── repository/       # Repository interface
│   └── usecase/          # Business logic (1 use case = 1 action)
├── presentation/
│   ├── genre/            # Genre list screen
│   ├── movies/           # Discover movies screen
│   └── detail/           # Movie detail screen
└── di/                   # Hilt modules
```

## Setup

1. Clone the repository
2. Get a free API key from [TMDB](https://www.themoviedb.org/settings/api)
3. Open `data/remote/interceptor/AuthInterceptor.kt`
4. Replace `YOUR_API_KEY` with your actual API key
5. Build and run

## Screens

| Screen | Description |
|--------|-------------|
| Genre List | Displays all official TMDB movie genres |
| Movie List | Discover movies filtered by selected genre |
| Movie Detail | Primary info, YouTube trailer, and user reviews |

## Requirements

- Android 8.0+ (API 26)
- Internet connection
