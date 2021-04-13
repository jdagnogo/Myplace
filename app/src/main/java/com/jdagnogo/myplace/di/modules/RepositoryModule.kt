package com.jdagnogo.myplace.di.modules

import android.content.Context
import com.google.gson.Gson
import com.jdagnogo.myplace.di.utils.API
import com.jdagnogo.myplace.di.utils.AppContext
import com.jdagnogo.myplace.repository.VenueRepository
import com.jdagnogo.myplace.repository.api.VenueApi
import com.jdagnogo.myplace.repository.data.MyPlaceDatabase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideDb(@AppContext context: Context) = MyPlaceDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideVenueRepository(
    ): VenueRepository =
        VenueRepository()

    @Singleton
    @Provides
    fun provideVenueDao(db: MyPlaceDatabase) = db.getVenueDao()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideAuthInterceptor() = HttpLoggingInterceptor()

    @API
    @Provides
    @Singleton
    fun provideOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(httpLoggingInterceptor)
        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideUserApi(
        @API okhttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ) = createRetrofit(okhttpClient, gsonConverterFactory).create(VenueApi::class.java)

    private fun createRetrofit(
        okhttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(VenueApi.BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
}