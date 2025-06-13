package com.example.tourguideapp.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tourguideapp.R
import com.example.tourguideapp.databinding.DialogTourDetailBinding
import com.example.tourguideapp.databinding.ItemTourBinding
import com.example.tourguideapp.model.Tour

class TourAdapter(private val onItemClick: (Tour) -> Unit) :
    ListAdapter<Tour, TourAdapter.TourViewHolder>(TourDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        val binding = ItemTourBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TourViewHolder(private val binding: ItemTourBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.detailButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val tour = getItem(position)
                    showTourDetailDialog(tour)
                }
            }
        }

        fun bind(tour: Tour) {
            with(binding) {
                // Load tour image
                Glide.with(tourImage)
                    .load(tour.imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(tourImage)

                // Set tour details
                tourName.text = tour.name
                tourLocation.text = tour.location
                tourDuration.text = root.context.getString(R.string.duration_format, tour.duration)
                tourRating.rating = tour.rating
                tourRatingLabel.text = String.format("%.1f", tour.rating).replace('.', ',')
                tourPrice.text = root.context.getString(R.string.tour_price, tour.price)
            }
        }

        private fun showTourDetailDialog(tour: Tour) {
            val dialogBinding = DialogTourDetailBinding.inflate(LayoutInflater.from(binding.root.context))
            val context = binding.root.context
            // Set data ke dialog
            Glide.with(context)
                .load(tour.imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(dialogBinding.imgTourDetail)
            dialogBinding.tvTourName.text = tour.name
            dialogBinding.tvTourLocation.text = tour.location
            dialogBinding.tvTourDuration.text = "Durasi: ${tour.duration}"
            dialogBinding.tvTourRating.text = "★ ${tour.rating}"
            dialogBinding.tvTourPrice.text = "Mulai dari Rp ${tour.price}"
            dialogBinding.tvTourDesc.text = tour.description
            // Tampilkan itinerary, included, excluded
            dialogBinding.tvItinerary.text = tour.itinerary.joinToString("\n")
            dialogBinding.tvIncluded.text = tour.included.joinToString("\n")
            dialogBinding.tvExcluded.text = tour.excluded.joinToString("\n")
            // Tampilkan dialog
            val dialog = AlertDialog.Builder(context)
                .setView(dialogBinding.root)
                .create()
            dialogBinding.btnCloseDialog.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }
    }

    private class TourDiffCallback : DiffUtil.ItemCallback<Tour>() {
        override fun areItemsTheSame(oldItem: Tour, newItem: Tour): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tour, newItem: Tour): Boolean {
            return oldItem == newItem
        }
    }
}