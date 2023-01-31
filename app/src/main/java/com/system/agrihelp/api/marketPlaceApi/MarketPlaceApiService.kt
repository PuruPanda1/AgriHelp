package com.system.agrihelp.api.marketPlaceApi

import com.system.agrihelp.data.models.LatestMarketDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MarketPlaceApiService {

    @GET("/resource/9ef84268-d588-465a-a308-a864a43d0070")
    suspend fun getMarketPlaceData(
        @Query("offset") offset: Int? = 0,
        @QueryMap map: HashMap<String, String>?,
        @Query("format") format: String? = "json",
        @Query("api-key") key: String? = "579b464db66ec23bdd000001cdd3946e44ce4aad7209ff7b23ac571b",
    ): Response<LatestMarketDataResponse>

}