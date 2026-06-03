# MovieApp

Android native application to discover movies using [TMDB API](https://api.themoviedb.org).

## Features

- Splash screen with app branding (icon + title + tagline)
- Browse official movie genres with color-coded cards
- Discover movies by genre with endless scrolling
- Search movies by title with debounced real-time paging
- Pull to refresh on movie list
- Scroll to top FAB on movie list
- View movie details with collapsing backdrop AppBar + gradient overlay
- Watch YouTube trailers (tap to open in YouTube app)
- Read user reviews with endless scrolling
- Visual 5-star rating display
- Shimmer loading effect on all screens
- Dark / Light theme toggle
- No internet connection detection with retry
- Full error, empty, and loading state handling
- Screen transition animations (slide + fade)

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
| Shimmer | compose-shimmer |
| Testing | JUnit4 + MockK + Turbine |

## Architecture

```
app/
├── data/
│   ├── remote/
│   │   ├── api/          # Retrofit interface + PagingSources (Discover + Search)
│   │   ├── dto/          # API response models (@Serializable)
│   │   └── interceptor/  # Auth interceptor (API key via BuildConfig)
│   ├── repository/       # Repository implementation
│   ├── mapper/           # DTO → Domain mappers
│   └── NetworkMonitor    # Real-time connectivity detection
├── domain/
│   ├── model/            # Pure Kotlin entities
│   ├── repository/       # Repository interface
│   └── usecase/          # 1 use case = 1 action
│       ├── GetGenresUseCase
│       ├── DiscoverMoviesUseCase
│       ├── SearchMoviesUseCase
│       ├── GetMovieDetailUseCase
│       ├── GetMovieReviewsUseCase
│       └── GetMovieTrailerUseCase
├── presentation/
│   ├── components/       # Reusable composables
│   │   ├── MoviePosterImage   # Image with placeholder + error fallback
│   │   ├── StarRating         # Visual 5-star rating
│   │   ├── ShimmerComponents  # Skeleton loading for all screens
│   │   ├── EmptyStateView     # Reusable empty state with icon + action
│   │   └── NoInternetScreen   # Offline state with retry
│   ├── genre/            # Genre list screen
│   ├── movies/           # Discover + search movies screen
│   ├── detail/           # Movie detail screen (collapsing AppBar)
│   ├── navigation/       # Screen routes
│   ├── theme/            # ThemeViewModel (dark/light toggle)
│   └── util/             # DateUtils (date formatting)
└── di/                   # Hilt modules (Network + Repository)
```

## Screens

| Screen | Description |
|--------|-------------|
| Splash | App icon + name + tagline (2s delay) |
| Genre List | Color-coded genre cards, shimmer loading, empty state |
| Movie List | Discover by genre, search with debounce, pull to refresh, scroll to top FAB |
| Movie Detail | Collapsing backdrop with gradient, trailer thumbnail, reviews paging |

## Setup

1. Clone the repository
   ```
   git clone https://github.com/kareem96/movie-app.git
   ```

2. Get a free API key from [TMDB](https://www.themoviedb.org/settings/api)

3. Add the API key to `local.properties` in the root project (create if not exists):
   ```
   TMDB_API_KEY=your_api_key_here
   ```

4. Build and run the project

## Testing

Unit tests are located in `app/src/test/`:

```
├── domain/usecase/
│   ├── GetGenresUseCaseTest       # Success + failure cases
│   └── GetMovieDetailUseCaseTest  # Success + failure cases
└── presentation/
    ├── genre/
    │   └── GenreViewModelTest     # Loading, success, error states
    └── movies/
        └── MoviesViewModelTest    # Search active/inactive, query, genreId
```

Run tests:
```
./gradlew test
```

## Requirements

- Android 8.0+ (API 26)
- Internet connection
- TMDB API key in `local.properties`
