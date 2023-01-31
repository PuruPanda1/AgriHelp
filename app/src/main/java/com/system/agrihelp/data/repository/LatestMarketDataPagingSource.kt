package com.system.agrihelp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.system.agrihelp.api.marketPlaceApi.MarketPlaceApiHelper
import com.system.agrihelp.data.models.Record

class LatestMarketDataPagingSource(
    private val apiHelper: MarketPlaceApiHelper,
    private val map: HashMap<String, String>
) :
    PagingSource<Int, Record>() {
    override fun getRefreshKey(state: PagingState<Int, Record>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Record> {
        return try {
            val position = params.key ?: 0
            val response = apiHelper.getMarketPlaceData(position,map).body()
            val results = response?.records ?: listOf()
            LoadResult.Page(
                data = results,
                prevKey = if (position == 0) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: java.lang.Exception) {
            LoadResult.Error(e)
        }
    }
}