package com.purabmodi.agrihelp.data.repository

import com.purabmodi.agrihelp.api.ApiHelper
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getNews(page:Int) = apiHelper.getNews(
        "agriculture",
        page.toString(),
        "5",
        "2130ffb189f6480eac0c267d69d6f6b8",
        "title"
    )

}
