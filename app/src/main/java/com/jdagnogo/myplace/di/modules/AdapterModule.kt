package com.jdagnogo.myplace.di.modules


import com.jdagnogo.myplace.ui.adapter.VenueAdapter
import com.jdagnogo.myplace.ui.adapter.VenueComparator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AdapterModule {
    @Provides
    @Singleton
    fun provideComparator(): VenueComparator = VenueComparator()


    @Provides
    @Singleton
    fun provideStoreListAdapter(comparator: VenueComparator): VenueAdapter =
        VenueAdapter(comparator)
}