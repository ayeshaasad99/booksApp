package com.example.booksapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Util(
    var smallThumbnail: String

) : Parcelable
