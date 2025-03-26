package ec.com.pablorcruh.watch_app.util

sealed class Screen(val route: String) {

    object Main: Screen("main")

    object Trending: Screen("trending")

    object Tv: Screen("tv")

    object Movies: Screen("movies")

}