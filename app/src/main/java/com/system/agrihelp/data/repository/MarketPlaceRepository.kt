package com.system.agrihelp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.system.agrihelp.api.marketPlaceApi.MarketPlaceApiHelper
import javax.inject.Inject

class MarketPlaceRepository @Inject constructor(private val apiHelper: MarketPlaceApiHelper) {

    fun getMarketPlaceData(map:HashMap<String,String>) = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 50),
        pagingSourceFactory = {LatestMarketDataPagingSource(apiHelper,map)}
    ).flow

}