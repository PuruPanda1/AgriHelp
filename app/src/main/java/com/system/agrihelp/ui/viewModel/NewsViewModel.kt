package com.system.agrihelp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.agrihelp.data.models.NewsResponse
import com.system.agrihelp.data.repository.NewsRepository
import com.system.agrihelp.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    private val _news = MutableLiveData<Resource<NewsResponse>>()
    val news: LiveData<Resource<NewsResponse>> get() = _news

    fun getNews(page: Int) = viewModelScope.launch {
        _news.postValue(Resource.loading(null))
        newsRepository.getNews(page).let {
            if (it.isSuccessful) {
                _news.postValue(Resource.success(it.body()))
            } else {
                _news.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }


}
