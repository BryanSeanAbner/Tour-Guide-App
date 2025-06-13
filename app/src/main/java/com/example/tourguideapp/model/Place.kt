package com.example.tourguideapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Place(
    val id: String,
    val name: String,
    val location: String,
    val description: String,
    val imageUrl: String,
    val rating: Float,
    val price: Double,
    val category: String,
    val openingHours: String,
    val address: String,
    val facilities: List<String>,
    val reviews: List<Review>,
    val distance: Float = 0f,
) : Parcelable

@Parcelize
data class Review(
    val id: String,
    val userName: String,
    val rating: Float,
    val comment: String,
    val date: String
) : Parcelable
