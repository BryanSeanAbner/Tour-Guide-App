package com.example.tourguideapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tour(
    val id: String,
    val name: String,
    val location: String,
    val description: String,
    val imageUrl: String,
    val rating: Float,
    val price: Int,
    val duration: String,
    val itinerary: List<String>,
    val included: List<String>,
    val excluded: List<String>
) : Parcelable 