package com.example.tourguideapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Banner(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val type: BannerType
) : Parcelable