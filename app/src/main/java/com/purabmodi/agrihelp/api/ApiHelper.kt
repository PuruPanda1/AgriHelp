package com.purabmodi.agrihelp.api

import com.purabmodi.agrihelp.data.models.NewsResponse
import retrofit2.Response
import retrofit2.http.Query

interface ApiHelper {
    suspend fun getNews(
        @Query("q") q: String,
        @Query("page") page: String,
        @Query("pageSize") pageSize: String,
        @Query("apiKey") apiKey: String,
        @Query("searchIn") searchIn: String
    ): Response<NewsResponse>

}