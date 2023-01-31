package com.system.agrihelp.api.newsApi

import com.system.agrihelp.data.models.NewsResponse
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

class NewsApiHelperImpl @Inject constructor(private val apiService: NewsApiService) : NewsApiHelper {
    override suspend fun getNews(
        @Query("q") q: String,
        @Query("page") page: String,
        @Query("pageSize") pageSize: String,
        @Query("apiKey") apiKey: String,
        @Query("searchIn") searchIn: String
    ): Response<NewsResponse> = apiService.getNews(q, page, pageSize, apiKey, searchIn)
}