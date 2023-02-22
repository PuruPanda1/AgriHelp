package com.purabmodi.agrihelp.api.marketPlaceApi

import com.purabmodi.agrihelp.data.models.LatestMarketDataResponse
import retrofit2.Response
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MarketPlaceApiHelper {
    suspend fun getMarketPlaceData(
        @Query("offset") offset: Int? = 0,
        @QueryMap map: HashMap<String, String>?,
        @Query("format") format: String? = "json",
        @Query("api-key") key: String? = "579b464db66ec23bdd000001cdd3946e44ce4aad7209ff7b23ac571b"
    ): Response<LatestMarketDataResponse>
}