package ec.com.pablorcruh.watch_app.main.presentation

import ec.com.pablorcruh.watch_app.main.domain.model.Media

data class MainState(

    val isLoading: Boolean = false,
    val isRefresing: Boolean = false,

    val trendingPage: Int =1,
    val tvPage: Int =1,
    val moviesPage: Int = 1,

    val trendingList: List<Media> = emptyList(),
    val tvList: List<Media> = emptyList(),
    val movieList: List<Media> = emptyList(),


    // 2 from trending + 2 from tv + 2 from movies
    val specialList: List<Media> = emptyList(),

    val name:String = "pablo"
)
