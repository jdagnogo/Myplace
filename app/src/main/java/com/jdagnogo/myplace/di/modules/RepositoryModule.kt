package com.jdagnogo.myplace.di.modules

import android.content.Context
import com.google.gson.Gson
import com.jdagnogo.myplace.di.utils.API
import com.jdagnogo.myplace.di.utils.AppContext
import com.jdagnogo.myplace.repository.VenueRepository
import com.jdagnogo.myplace.repository.api.VenueApi
import com.jdagnogo.myplace.repository.api.VenueDetailsApi
import com.jdagnogo.myplace.repository.api.VenueMapper
import com.jdagnogo.myplace.repository.api.VenueRemoteData
import com.jdagnogo.myplace.repository.data.MyPlaceDatabase
import com.jdagnogo.myplace.repository.data.VenueDao
import com.jdagnogo.myplace.repository.data.VenueDetailsDao
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
            remoteData: VenueRemoteData,
            venueDao: VenueDao,
            venueDetailsDao: VenueDetailsDao
    ): VenueRepository =
            VenueRepository(remoteData, venueDao, venueDetailsDao)

    @Singleton
    @Provides
    fun provideVenueDao(db: MyPlaceDatabase) = db.getVenueDao()

    @Singleton
    @Provides
    fun provideVenueDetailsDao(db: MyPlaceDatabase) = db.getVenueDetailsDao()

    @Singleton
    @Provides
    fun provideVenueRemoteData(venueApi: VenueApi, venueDetailsApi: VenueDetailsApi, venueMapper: VenueMapper) =
            VenueRemoteData(venueApi, venueDetailsApi, venueMapper)

    @Singleton
    @Provides
    fun provideVenueMapper() = VenueMapper()

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
    fun provideVenueApi(
        @API okhttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ) = createRetrofit(okhttpClient, gsonConverterFactory).create(VenueApi::class.java)

    @Singleton
    @Provides
    fun provideVenueDetailsApi(
            @API okhttpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory
    ) = createRetrofit(okhttpClient, gsonConverterFactory).create(VenueDetailsApi::class.java)

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