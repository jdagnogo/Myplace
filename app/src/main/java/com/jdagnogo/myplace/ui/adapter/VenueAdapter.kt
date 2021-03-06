package com.jdagnogo.myplace.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jdagnogo.myplace.model.Venue
import javax.inject.Inject

/**
 * Listener when we click on a item from the listApapter
 */
interface VenueListener {
    fun onClick(venue: Venue)
}

/**
 * We are using a ListAdapter.
 */
class VenueAdapter @Inject constructor(cartItemsComparator: VenueComparator) :
    ListAdapter<Venue, RecyclerView.ViewHolder>(cartItemsComparator) {
    lateinit var listener: VenueListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VenueViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VenueViewHolder).bind(getItem(position))
    }
}