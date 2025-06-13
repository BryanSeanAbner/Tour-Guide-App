package com.example.tourguideapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tourguideapp.R
import com.example.tourguideapp.model.Place

class PlaceAdapter(
    private val onPlaceClick: (Place) -> Unit
) : ListAdapter<Place, PlaceAdapter.PlaceViewHolder>(PlaceDiffCallback()) {

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.placeImage)
        private val nameView: TextView = itemView.findViewById(R.id.placeName)
        private val locationView: TextView = itemView.findViewById(R.id.placeLocation)
        private val ratingView: RatingBar = itemView.findViewById(R.id.placeRating)
        private val priceView: TextView = itemView.findViewById(R.id.placePrice)

        fun bind(place: Place) {
            nameView.text = place.name
            locationView.text = place.location
            ratingView.rating = place.rating
            priceView.text = "Rp ${place.price}"

            Glide.with(itemView.context)
                .load(place.imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imageView)

            itemView.setOnClickListener { onPlaceClick(place) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_place, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private class PlaceDiffCallback : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem == newItem
    }
} 