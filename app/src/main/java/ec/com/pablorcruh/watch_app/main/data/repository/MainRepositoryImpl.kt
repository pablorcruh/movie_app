package ec.com.pablorcruh.watch_app.main.data.repository

import android.app.Application
import ec.com.pablorcruh.watch_app.main.data.local.MediaDatabase
import ec.com.pablorcruh.watch_app.main.data.remote.api.MediaApi
import ec.com.pablorcruh.watch_app.main.domain.model.Media
import ec.com.pablorcruh.watch_app.main.domain.repository.MainRepository
import ec.com.pablorcruh.watch_app.util.Resource
import kotlinx.coroutines.flow.Flow

class MainRepositoryImpl(
    private val application: Application,
    private val madiaApi: MediaApi,
    mediaDatabase: MediaDatabase
): MainRepository {

    private val mediaDao = mediaDatabase.mediaDao

    override suspend fun upsertMediaList(mediaEntities: List<Media>) {
        TODO("Not yet implemented")
    }

    override suspend fun upsertMediaItem(mediaItem: Media) {
        TODO("Not yet implemented")
    }

    override suspend fun getMediaListByCategory(category: String): List<Media> {
        TODO("Not yet implemented")
    }

    override suspend fun getMoviesAndTv(
        forceFetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        category: String,
        page: Int
    ): Flow<Resource<List<Media>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrending(
        forceFetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        time: String,
        page: Int
    ): Flow<Resource<List<Media>>> {
        TODO("Not yet implemented")
    }
}