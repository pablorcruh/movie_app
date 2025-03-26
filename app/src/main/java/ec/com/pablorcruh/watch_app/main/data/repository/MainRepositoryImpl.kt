package ec.com.pablorcruh.watch_app.main.data.repository

import android.app.Application
import ec.com.pablorcruh.watch_app.R
import ec.com.pablorcruh.watch_app.main.data.local.MediaDatabase
import ec.com.pablorcruh.watch_app.main.data.mappers.toMedia
import ec.com.pablorcruh.watch_app.main.data.mappers.toMediaEntity
import ec.com.pablorcruh.watch_app.main.data.remote.api.MediaApi
import ec.com.pablorcruh.watch_app.main.domain.model.Media
import ec.com.pablorcruh.watch_app.main.domain.repository.MainRepository
import ec.com.pablorcruh.watch_app.util.APIConstants.MOVIE
import ec.com.pablorcruh.watch_app.util.APIConstants.POPULAR
import ec.com.pablorcruh.watch_app.util.APIConstants.TRENDING
import ec.com.pablorcruh.watch_app.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val application: Application,
    private val mediaApi: MediaApi,
    mediaDatabase: MediaDatabase
): MainRepository {

    private val mediaDao = mediaDatabase.mediaDao

    override suspend fun upsertMediaList(mediaList: List<Media>) {
        mediaDao.upsertMediaList(mediaList.map{it.toMediaEntity()})
    }

    override suspend fun upsertMediaItem(mediaItem: Media) {
        mediaDao.upsertMediaItem(mediaItem.toMediaEntity())
    }

    override suspend fun getMediaListByCategory(category: String): List<Media> {
        return mediaDao.getMediaListByCategory(category).map { it.toMedia() }
    }

    override suspend fun getMoviesAndTv(
        forceFetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        category: String,
        page: Int
    ): Flow<Resource<List<Media>>> {
        return flow{

            emit(Resource.Loading(true))

            val localMediaList = mediaDao.getMediaListByTypeAndCategory(type, POPULAR)

            val loadJustFromCache =
                localMediaList.isNotEmpty() &&
                        !forceFetchFromRemote &&
                        !isRefresh
            if(loadJustFromCache){
                emit(Resource.Success(localMediaList.map { it.toMedia() }))
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteMediaList = try{
                mediaApi.getMoviesAndTv(
                    type, POPULAR, if(isRefresh) 1 else page
                )?.results
            }catch(e: IOException){
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.could_not_load_data_from_remote)))
                emit(Resource.Loading(false))
                return@flow
            }catch(e: HttpException){
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.could_not_load_data_from_remote)))
                emit(Resource.Loading(false))
                return@flow
            }catch(e: Exception){
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.could_not_load_data_from_remote)))
                emit(Resource.Loading(false))
                return@flow
            }

            remoteMediaList?.let { mediaDtos ->
                val entities = mediaDtos.map { mediaDto ->
                    mediaDto.toMediaEntity(
                        type,
                        POPULAR
                    )
                }

                if(isRefresh){
                    mediaDao.deleteAllMediaItemsByTypeAndCategory(type, POPULAR)
                }

                mediaDao.upsertMediaList(entities)

                emit(Resource.Success(entities.map { it.toMedia() }))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error(application.getString(R.string.could_not_load_data_from_remote)))
            emit(Resource.Loading(false))
            return@flow


        }

    }

    override suspend fun getTrending(
        forceFetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        time: String,
        page: Int
    ): Flow<Resource<List<Media>>> {
        return flow {
            emit(Resource.Loading(true))

            val localMediaList = mediaDao.getMediaListByCategory(TRENDING)

            val loadJustFromCache =
                localMediaList.isNotEmpty() &&
                        !forceFetchFromRemote &&
                        !isRefresh
            if(loadJustFromCache){
                emit(Resource.Success(localMediaList.map { it.toMedia() }))
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteMediaList = try{
                mediaApi.getTrending(
                    type,time, if(isRefresh) 1 else page
                )?.results
            }catch(e: IOException){
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.could_not_load_data_from_remote)))
                emit(Resource.Loading(false))
                return@flow
            }catch(e: HttpException){
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.could_not_load_data_from_remote)))
                emit(Resource.Loading(false))
                return@flow
            }catch(e: Exception){
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.could_not_load_data_from_remote)))
                emit(Resource.Loading(false))
                return@flow
            }

            remoteMediaList?.let { mediaDtos ->
                val entities = mediaDtos.map { mediaDto ->
                    mediaDto.toMediaEntity(
                        type = mediaDto.media_type ?: MOVIE,
                        category = TRENDING
                    )
                }

                if(isRefresh){
                    mediaDao.deleteAllMediaItemsByCategory(TRENDING)
                }

                mediaDao.upsertMediaList(entities)

                emit(Resource.Success(entities.map { it.toMedia() }))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error(application.getString(R.string.could_not_load_data_from_remote)))
            emit(Resource.Loading(false))
            return@flow

        }
    }
}