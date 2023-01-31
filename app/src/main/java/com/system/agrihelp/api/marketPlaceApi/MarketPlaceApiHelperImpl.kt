package com.system.agrihelp.api.marketPlaceApi

import com.system.agrihelp.data.models.LatestMarketDataResponse
import retrofit2.Response
import javax.inject.Inject

class MarketPlaceApiHelperImpl @Inject constructor(private val apiService: MarketPlaceApiService) :
    MarketPlaceApiHelper {
    override suspend fun getMarketPlaceData(
        offset: Int?,
        map: HashMap<String, String>?,
        format: String?,
        key: String?
    ): Response<LatestMarketDataResponse> = apiService.getMarketPlaceData(offset, map, format, key)

}