package com.example.tourguideapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.tourguideapp.R
import com.example.tourguideapp.model.Place
import com.example.tourguideapp.model.Tour

class DetailActivity : AppCompatActivity() {
    private var place: Place? = null
    private var tour: Tour? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        place = intent.getParcelableExtra("place", Place::class.java)
        tour = intent.getParcelableExtra("tour", Tour::class.java)

        if (place == null && tour == null) {
            finish()
            return
        }

        setupViews()
        loadData()
    }

    private fun setupViews() {
        supportActionBar?.apply {
            title = place?.name ?: tour?.name
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun loadData() {
        if (place != null) {
            loadPlaceData()
        } else if (tour != null) {
            loadTourData()
        }
    }

    private fun loadPlaceData() {
        place?.let { p ->
            val imageView = findViewById<ImageView>(R.id.placeImage)
            val nameView = findViewById<TextView>(R.id.placeName)
            val distanceView = findViewById<TextView>(R.id.placeDistance)
            val ratingBar = findViewById<RatingBar>(R.id.placeRating)
            val descriptionView = findViewById<TextView>(R.id.placeDescription)
            val priceView = findViewById<TextView>(R.id.placePrice)
            val addressView = findViewById<TextView>(R.id.placeAddress)
            val openingHoursView = findViewById<TextView>(R.id.placeOpeningHours)

            // Load image using Glide
            Glide.with(this)
                .load(p.imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imageView)

            nameView.text = p.name
            distanceView.text = String.format("%.1f km", p.distance)
            ratingBar.rating = p.rating
            descriptionView.text = p.description
            priceView.text = "Rp ${p.price}"
            addressView.text = p.address
            openingHoursView.text = p.openingHours
        }
    }

    private fun loadTourData() {
        tour?.let { t ->
            val imageView = findViewById<ImageView>(R.id.placeImage)
            val nameView = findViewById<TextView>(R.id.placeName)
            val typeView = findViewById<TextView>(R.id.placeType)
            val distanceView = findViewById<TextView>(R.id.placeDistance)
            val ratingBar = findViewById<RatingBar>(R.id.placeRating)
            val descriptionView = findViewById<TextView>(R.id.placeDescription)
            val priceView = findViewById<TextView>(R.id.placePrice)
            val addressView = findViewById<TextView>(R.id.placeAddress)
            val openingHoursView = findViewById<TextView>(R.id.placeOpeningHours)

            // Load image using Glide
            Glide.with(this)
                .load(t.imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imageView)

            nameView.text = t.name
            typeView.text = t.location
            distanceView.text = getString(R.string.tour_duration, t.duration)
            ratingBar.rating = t.rating
            descriptionView.text = t.description
            priceView.text = "Rp ${t.price}"
            addressView.text = t.location
            openingHoursView.text = "${t.duration} hari"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun newIntent(context: Context, place: Place): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra("place", place)
            }
        }
    }
} 