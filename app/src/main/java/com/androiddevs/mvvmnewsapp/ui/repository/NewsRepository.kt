package com.androiddevs.mvvmnewsapp.ui.repository

import com.androiddevs.mvvmnewsapp.ui.api.RetrofitInstance
import com.androiddevs.mvvmnewsapp.ui.db.articleDatabase
import com.androiddevs.mvvmnewsapp.ui.models.Article
import com.androiddevs.mvvmnewsapp.ui.models.NewsResponse
import retrofit2.Response

class NewsRepository(
   val database  : articleDatabase
) {

   suspend fun getBreakingNews(countryCode:String,pageNumber:Int):Response<NewsResponse>{
     return RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)
   }

    suspend fun searchNews(searchQuery:String,pageNumber:Int):Response<NewsResponse>{
        return RetrofitInstance.api.searchForNews(searchQuery,pageNumber)
    }

    suspend fun insert(article:Article)
    {
        database.getArticleDAO().insert(article)
    }

    fun getSavedNews() = database.getArticleDAO().getAllArticles()

    suspend fun delete(article:Article){
        database.getArticleDAO().delete(article)
    }

}