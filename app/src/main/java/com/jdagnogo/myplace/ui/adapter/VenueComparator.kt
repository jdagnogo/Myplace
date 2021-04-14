package com.jdagnogo.myplace.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.jdagnogo.myplace.model.Venue

class VenueComparator : DiffUtil.ItemCallback<Venue>() {
    override fun areItemsTheSame(oldItem: Venue, newItem: Venue): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Venue, newItem: Venue): Boolean {
        return oldItem == newItem
    }
}