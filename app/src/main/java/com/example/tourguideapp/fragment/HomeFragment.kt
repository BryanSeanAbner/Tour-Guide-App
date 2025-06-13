package com.example.tourguideapp.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.tourguideapp.R
import com.example.tourguideapp.activity.DetailActivity
import com.example.tourguideapp.adapter.BannerAdapter
import com.example.tourguideapp.adapter.CategoryAdapter
import com.example.tourguideapp.adapter.TourAdapter
import com.example.tourguideapp.data.DummyData
import com.example.tourguideapp.databinding.FragmentHomeBinding
import com.example.tourguideapp.model.Banner
import com.example.tourguideapp.model.BannerType
import com.example.tourguideapp.model.Category
import com.example.tourguideapp.model.Tour
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import androidx.recyclerview.widget.RecyclerView
import com.example.tourguideapp.viewmodel.HomeViewModel
import android.content.Context

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var bannerViewPager: ViewPager2
    private lateinit var searchInput: TextInputEditText
    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var recommendedToursRecyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var tourAdapter: TourAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        observeViewModel()
        setupBannerCarousel()
        setupSearch()
        setupCategories()
        setupRecommendedTours()
    }

    private fun setupRecyclerViews() {
        bannerViewPager = binding.bannerViewPager
        searchInput = binding.searchInput
        categoriesRecyclerView = binding.categoriesRecyclerView
        recommendedToursRecyclerView = binding.recommendedToursRecyclerView

        // Setup TourAdapter
        tourAdapter = TourAdapter { tour ->
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("tour_id", tour.id)
                putExtra("tour_name", tour.name)
                putExtra("tour_description", tour.description)
                putExtra("tour_image", tour.imageUrl)
                putExtra("tour_price", tour.price)
                putExtra("tour_rating", tour.rating)
                putExtra("tour_duration", tour.duration)
                putExtra("tour_location", tour.location)
            }
            startActivity(intent)
        }

        recommendedToursRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = tourAdapter
        }
    }

    private fun setupBannerCarousel() {
        val bannerAdapter = BannerAdapter(DummyData.banners) { banner ->
            handleBannerClick(banner)
        }
        
        bannerViewPager.apply {
            adapter = bannerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            
            // Auto scroll setiap 3 detik
            val autoScrollHandler = Handler()
            val autoScrollRunnable = object : Runnable {
                override fun run() {
                    if (currentItem == DummyData.banners.size - 1) {
                        currentItem = 0
                    } else {
                        currentItem++
                    }
                    autoScrollHandler.postDelayed(this, 3000)
                }
            }
            autoScrollHandler.postDelayed(autoScrollRunnable, 3000)
        }
    }

    private fun setupSearch() {
        searchInput.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = textView.text.toString()
                if (query.isNotEmpty()) {
                    handleSearch(query)
                }
                true
            } else {
                false
            }
        }
    }

    private fun setupCategories() {
        val categories = listOf(
            Category("Gunung", R.drawable.ic_mountain),
            Category("Candi", R.drawable.ic_temple),
            Category("Kuliner", R.drawable.ic_culinary)
        )

        categoriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CategoryAdapter(categories) { category ->
                handleCategoryClick(category)
            }
        }
    }

    private fun setupRecommendedTours() {
        recommendedToursRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = tourAdapter
            tourAdapter.submitList(DummyData.tours)
        }
    }

    private fun handleBannerClick(banner: Banner) {
        when (banner.type) {
            BannerType.TEMPATWISATA -> {
                Snackbar.make(requireView(), "Tempat Wisata: ${banner.title}", Snackbar.LENGTH_SHORT).show()
            }
            BannerType.DESTINATION -> {
                Snackbar.make(requireView(), "Destinasi: ${banner.title}", Snackbar.LENGTH_SHORT).show()
            }
            BannerType.KULINER -> {
                Snackbar.make(requireView(), "Kuliner: ${banner.title}", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleSearch(query: String) {
        Snackbar.make(requireView(), "Mencari: $query", Snackbar.LENGTH_SHORT).show()
    }

    private fun handleCategoryClick(category: Category) {
        // Simpan argument kategori ke SharedPreferences
        val sharedPref = requireActivity().getSharedPreferences("tour_guide_prefs", Context.MODE_PRIVATE)
        sharedPref.edit().putString("scrollToCategory", category.name.lowercase()).apply()
        // Pindah tab ke Destinasi lewat bottom navigation
        (activity as com.example.tourguideapp.activity.MainActivity).binding.bottomNavigation.selectedItemId = R.id.nav_destinations
    }

    private fun handleTourClick(tour: Tour) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra("tour_id", tour.id)
            putExtra("tour_name", tour.name)
            putExtra("tour_description", tour.description)
            putExtra("tour_image", tour.imageUrl)
            putExtra("tour_price", tour.price)
            putExtra("tour_rating", tour.rating)
            putExtra("tour_duration", tour.duration)
            putExtra("tour_location", tour.location)
        }
        startActivity(intent)
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
 