package com.system.agrihelp.data.repository

import com.system.agrihelp.api.newsApi.NewsApiHelper
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiHelper: NewsApiHelper) {
    suspend fun getNews(page:Int) = apiHelper.getNews(
        "agriculture",
        page.toString(),
        "15",
        "2130ffb189f6480eac0c267d69d6f6b8",
        "title"
    )

}
