package ec.com.pablorcruh.watch_app.main.domain.repository

import ec.com.pablorcruh.watch_app.main.domain.model.Media
import ec.com.pablorcruh.watch_app.util.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun upsertMediaList(mediaEntities: List<Media>)

    suspend fun upsertMediaItem(mediaItem: Media)

    suspend fun getMediaListByCategory(category: String): List<Media>

    suspend fun getMoviesAndTv(
        forceFetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        category: String,
        page: Int
    ): Flow<Resource<List<Media>>>

    suspend fun getTrending(
        forceFetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        time: String,
        page: Int
    ):Flow<Resource<List<Media>>>
}