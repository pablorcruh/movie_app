package ec.com.pablorcruh.watch_app.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ec.com.pablorcruh.watch_app.main.domain.model.Media
import ec.com.pablorcruh.watch_app.main.domain.repository.MainRepository
import ec.com.pablorcruh.watch_app.util.APIConstants.ALL
import ec.com.pablorcruh.watch_app.util.APIConstants.MOVIE
import ec.com.pablorcruh.watch_app.util.APIConstants.POPULAR
import ec.com.pablorcruh.watch_app.util.APIConstants.TRENDING_TIME
import ec.com.pablorcruh.watch_app.util.APIConstants.TV
import ec.com.pablorcruh.watch_app.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    init {
        loadTrending()
        loadTv()
        loadMovies()
    }

    fun event(mainUIEvents: MainUIEvents){
        when(mainUIEvents){
            is MainUIEvents.Paginate -> TODO()
            is MainUIEvents.Refresh -> TODO()
        }
    }


    private fun loadTrending(
        forceFetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ){
        viewModelScope.launch {
            mainRepository.getTrending(
                forceFetchFromRemote,
                isRefresh,
                ALL,
                TRENDING_TIME,
                mainState.value.trendingPage
            ).collect{ result ->
                when(result){
                    is Resource.Error<*> -> Unit
                    is Resource.Loading<*> -> {
                        _mainState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Resource.Success<*> -> {
                        result.data?.let{mediaList ->
                            val shuffledList = mediaList.shuffled()

                            if(isRefresh){
                                _mainState.update {
                                    it.copy(
                                        trendingList = shuffledList,
                                        trendingPage = 2
                                    )
                                }
                                loadSpecialList(shuffledList)
                            }else{
                                _mainState.update {
                                    it.copy(
                                        trendingList = it.trendingList + shuffledList,
                                        trendingPage = it.trendingPage + 1
                                    )
                                }
                            }
                            if(!forceFetchFromRemote){
                                loadSpecialList(shuffledList)
                            }

                        }
                    }
                }
            }
        }
    }



    private fun loadTv(
        forceFetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ){
        viewModelScope.launch {
            mainRepository.getMoviesAndTv(
                forceFetchFromRemote,
                isRefresh,
                TV,
                POPULAR,
                mainState.value.tvPage
            ).collect{ result ->
                when(result){
                    is Resource.Error<*> -> Unit
                    is Resource.Loading<*> -> {
                        _mainState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Resource.Success<*> -> {
                        result.data?.let{mediaList ->
                            val shuffledList = mediaList.shuffled()

                            if(isRefresh){
                                _mainState.update {
                                    it.copy(
                                        tvList = shuffledList,
                                        tvPage = 2
                                    )
                                }
                                loadSpecialList(shuffledList)
                            }else{
                                _mainState.update {
                                    it.copy(
                                        tvList = it.tvList + shuffledList,
                                        tvPage = it.tvPage + 1
                                    )
                                }
                            }
                            if(!forceFetchFromRemote){
                                loadSpecialList(shuffledList)
                            }
                        }
                    }
                }
            }
        }
    }


    private fun loadMovies(
        forceFetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ){
        viewModelScope.launch {
            mainRepository.getMoviesAndTv(
                forceFetchFromRemote,
                isRefresh,
                MOVIE,
                POPULAR,
                mainState.value.moviesPage
            ).collect{ result ->
                when(result){
                    is Resource.Error<*> -> Unit
                    is Resource.Loading<*> -> {
                        _mainState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Resource.Success<*> -> {
                        result.data?.let{mediaList ->
                            val shuffledList = mediaList.shuffled()

                            if(isRefresh){
                                _mainState.update {
                                    it.copy(
                                        movieList = shuffledList,
                                        moviesPage = 2
                                    )
                                }
                                loadSpecialList(shuffledList)
                            }else{
                                _mainState.update {
                                    it.copy(
                                        movieList = it.movieList + shuffledList,
                                        moviesPage = it.moviesPage + 1
                                    )
                                }
                            }
                            if(!forceFetchFromRemote){
                                loadSpecialList(shuffledList)
                            }
                        }
                    }
                }
            }
        }
    }


    private fun loadSpecialList(list: List<Media>){
        if(mainState.value.specialList.size >=4){
            _mainState.update {
                it.copy(
                    specialList = it.specialList + list.take(2)
                )
            }
        }
    }

}