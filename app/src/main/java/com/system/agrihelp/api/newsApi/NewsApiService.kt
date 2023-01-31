package com.system.agrihelp.api.newsApi

import com.system.agrihelp.data.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("everything/")
    suspend fun getNews(
        @Query("q") q: String,
        @Query("page") page: String,
        @Query("pageSize") pageSize: String,
        @Query("apiKey") apiKey: String,
        @Query("searchIn") searchIn: String
    ): Response<NewsResponse>

}