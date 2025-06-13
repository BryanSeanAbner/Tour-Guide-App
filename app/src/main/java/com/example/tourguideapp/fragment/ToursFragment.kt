package com.example.tourguideapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tourguideapp.activity.DetailActivity
import com.example.tourguideapp.adapter.TourAdapter
import com.example.tourguideapp.databinding.FragmentToursBinding
import com.example.tourguideapp.viewmodel.HomeViewModel

class ToursFragment : Fragment() {
    private var _binding: FragmentToursBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var tourAdapter: TourAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        tourAdapter = TourAdapter { tour ->
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("tour", tour)
            }
            startActivity(intent)
        }

        binding.toursRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tourAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.tours.observe(viewLifecycleOwner) { tours ->
            tourAdapter.submitList(tours)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 