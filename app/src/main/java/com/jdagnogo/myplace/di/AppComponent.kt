package com.jdagnogo.myplace.di

import com.jdagnogo.myplace.MyPlaceApplication
import com.jdagnogo.myplace.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        ApplicationModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        AdapterModule::class,
        RepositoryModule::class,
        AndroidSupportInjectionModule::class
    ]
)
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MyPlaceApplication): Builder

        fun build(): AppComponent
    }

    fun inject(application: MyPlaceApplication)
}