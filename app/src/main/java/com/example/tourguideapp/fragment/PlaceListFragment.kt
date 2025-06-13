package com.example.tourguideapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tourguideapp.adapter.PlaceAdapter
import com.example.tourguideapp.data.DummyData
import com.example.tourguideapp.databinding.FragmentPlaceListBinding
import com.example.tourguideapp.model.Place

class PlaceListFragment : Fragment() {
    private var _binding: FragmentPlaceListBinding? = null
    private val binding get() = _binding!!
    private lateinit var placeAdapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        placeAdapter = PlaceAdapter { place ->
            // Handle place click
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = placeAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}