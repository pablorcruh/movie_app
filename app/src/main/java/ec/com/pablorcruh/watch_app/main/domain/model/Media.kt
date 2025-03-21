package ec.com.pablorcruh.watch_app.main.domain.model

data class Media(
    val adult: Boolean,
    val backdropPath: String,
    val genres: List<String>,
    val mediaType: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val category: String
)
