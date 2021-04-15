package com.jdagnogo.myplace.di.modules

import com.jdagnogo.myplace.ui.MainActivity
import com.jdagnogo.myplace.ui.fragment.HomeFragment
import com.jdagnogo.myplace.ui.fragment.VenueDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector()
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector()
    abstract fun contributeVenueDetailsFragment(): VenueDetailsFragment
}