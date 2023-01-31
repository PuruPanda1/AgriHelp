package com.system.agrihelp.di

import com.system.agrihelp.BuildConfig
import com.system.agrihelp.api.marketPlaceApi.MarketPlaceApiHelper
import com.system.agrihelp.api.marketPlaceApi.MarketPlaceApiHelperImpl
import com.system.agrihelp.api.marketPlaceApi.MarketPlaceApiService
import com.system.agrihelp.api.newsApi.NewsApiHelper
import com.system.agrihelp.api.newsApi.NewsApiHelperImpl
import com.system.agrihelp.api.newsApi.NewsApiService
import com.system.agrihelp.data.repository.MarketPlaceRepository
import com.system.agrihelp.data.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val newsBaseUrl = "https://newsapi.org/v2/"
    val marketPlaceBaseUrl = "https://api.data.gov.in/"

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    } else {
        OkHttpClient.Builder()
            .build()
    }

    @Singleton
    @Provides
    @Named("newsRetrofit")
    fun provideRetorfit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(newsBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(@Named("newsRetrofit") retrofit: Retrofit) =
        retrofit.create(NewsApiService::class.java)

    @Singleton
    @Provides
    fun provideApiHelper(apiHelper: NewsApiHelperImpl) = apiHelper as NewsApiHelper

    @Singleton
    @Provides
    fun provideNewsRepository(apiHelper: NewsApiHelper) =
        NewsRepository(apiHelper)

    @Singleton
    @Provides
    @Named("marketPlaceRetrofit")
    fun provideRetorfit2(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(marketPlaceBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideMarketPlaceApiService(@Named("marketPlaceRetrofit") retrofit: Retrofit) =
        retrofit.create(MarketPlaceApiService::class.java)

    @Singleton
    @Provides
    fun provideMarketPlaceApiHelper(apiHelper: MarketPlaceApiHelperImpl) =
        apiHelper as MarketPlaceApiHelper

    @Singleton
    @Provides
    fun provideMarketPlaceRepository(apiHelper: MarketPlaceApiHelper) =
        MarketPlaceRepository(apiHelper)
}