package com.jdagnogo.myplace.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jdagnogo.myplace.R
import com.jdagnogo.myplace.model.Venue
import kotlinx.android.synthetic.main.item_venue.view.*


class VenueViewHolder(view: View, var listener: VenueListener) :
    RecyclerView.ViewHolder(view) {
    companion object {
        fun create(
            parent: ViewGroup, listener: VenueListener
        ): VenueViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_venue, parent, false)
            return VenueViewHolder(view, listener)
        }
    }

    fun bind(venue: Venue) {
        with(itemView) {
            venue_container.setOnClickListener {
                listener.onClick(venue)
            }
            venue_title.text = venue.name
        }
    }
}