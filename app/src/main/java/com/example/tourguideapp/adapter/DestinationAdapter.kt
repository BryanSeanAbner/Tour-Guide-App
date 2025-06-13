package com.example.tourguideapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tourguideapp.R
import com.example.tourguideapp.model.Destination

class DestinationAdapter(
    private val destinations: List<Destination>,
    private val onDestinationClick: (Destination) -> Unit
) : RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder>() {

    inner class DestinationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.destinationImage)
        private val nameView: TextView = itemView.findViewById(R.id.destinationName)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.destinationRating)
        private val ratingValue: TextView = itemView.findViewById(R.id.destinationRatingValue)
        private val cityView: TextView = itemView.findViewById(R.id.destinationCity)

        fun bind(destination: Destination) {
            if (destination.imageUrl.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(destination.imageUrl)
                    .centerCrop()
                    .error(android.R.drawable.ic_menu_gallery)
                    .into(imageView)
            } else {
                imageView.setImageResource(android.R.drawable.ic_menu_gallery)
            }

            nameView.text = destination.name
            cityView.text = destination.city

            ratingBar.rating = destination.rating
            ratingValue.text = String.format("%.2f", destination.rating).replace('.', ',')

            itemView.setOnClickListener { onDestinationClick(destination) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_destination, parent, false)
        return DestinationViewHolder(view)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        holder.bind(destinations[position])
    }

    override fun getItemCount(): Int = destinations.size
} 