package com.example.tourguideapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.tourguideapp.data.DummyData
import com.example.tourguideapp.databinding.FragmentTourDetailBinding
import com.google.android.material.snackbar.Snackbar

class TourDetailFragment : Fragment() {
    private var _binding: FragmentTourDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTourDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val tourId = arguments?.getString("tourId") ?: ""
        val tour = DummyData.tours.find { it.id == tourId }

        if (tour != null) {
            with(binding) {
                Glide.with(requireContext())
                    .load(tour.imageUrl)
                    .into(tourImage)

                tourName.text = tour.name
                tourDescription.text = tour.description
                tourPrice.text = tour.price.toString()
                tourRating.rating = tour.rating
                tourDuration.text = tour.duration
                tourLocation.text = tour.location

                bookingButton.setOnClickListener {
                    Snackbar.make(view, "Fitur booking akan segera tersedia!", Snackbar.LENGTH_SHORT).show()
                }
            }
        } else {
            Snackbar.make(view, "Tour tidak ditemukan", Snackbar.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(tourId: String) = TourDetailFragment().apply {
            arguments = Bundle().apply {
                putString("tourId", tourId)
            }
        }
    }
} 