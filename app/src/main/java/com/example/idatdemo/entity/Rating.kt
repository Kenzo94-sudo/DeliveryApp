package com.example.idatdemo.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rating(

    val rate: Double,

    val count: Int
) : Parcelable
