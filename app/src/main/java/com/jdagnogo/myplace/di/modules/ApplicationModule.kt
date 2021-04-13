package com.jdagnogo.myplace.di.modules

import android.content.Context
import com.jdagnogo.myplace.MyPlaceApplication
import com.jdagnogo.myplace.di.utils.AppContext

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {
    @AppContext
    @Provides
    @Singleton
    fun provideContext(application: MyPlaceApplication): Context = application
}