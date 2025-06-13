package com.example.tourguideapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Destination(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val city: String,
    val rating: Float,
    val category: String,
    val activities: List<String> = emptyList()
) : Parcelable 