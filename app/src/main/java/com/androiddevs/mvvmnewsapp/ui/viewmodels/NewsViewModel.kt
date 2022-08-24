package com.androiddevs.mvvmnewsapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.ui.models.Article
import com.androiddevs.mvvmnewsapp.ui.models.NewsResponse
import com.androiddevs.mvvmnewsapp.ui.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.ui.utils.Resources
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository) : ViewModel() {

    init {
        getBreakingNews("in")
    }

        val BreakingNews : MutableLiveData<Resources<NewsResponse>> = MutableLiveData()
        val SearchNews : MutableLiveData<Resources<NewsResponse>> = MutableLiveData()
        val pageNumber = 1



        fun getBreakingNews(countryCode:String) = viewModelScope.launch {
           // BreakingNews.postValue(Resources.Loading())
            val response = newsRepository.getBreakingNews(countryCode,pageNumber)
            BreakingNews.postValue(handleBreakingNewsResponse(response))
        }

        fun searchNews(searchQuery: String) = viewModelScope.launch {
            val response = newsRepository.searchNews(searchQuery,pageNumber)
            SearchNews.postValue(handleSearchNewsResponse(response))
        }

    private fun handleBreakingNewsResponse(response:Response<NewsResponse>):Resources<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {resultResponse ->
                return Resources.Success(resultResponse)
            }
        }

        return Resources.Error(response.message())
    }
    private fun handleSearchNewsResponse(response:Response<NewsResponse>):Resources<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {resultResponse ->
                return Resources.Success(resultResponse)
            }
        }
        return Resources.Error(response.message())
    }

    fun insert(article: Article) = viewModelScope.launch {
        newsRepository.insert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun delete(article: Article) = viewModelScope.launch {
        newsRepository.delete(article)
    }
}
