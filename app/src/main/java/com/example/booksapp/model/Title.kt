package com.example.booksapp.model


import android.os.Parcelable
import androidx.room.Embedded
import com.example.booksapp.model.Util
import kotlinx.parcelize.Parcelize

@Parcelize
data class Title(
    var title: String,
    @Embedded val imageLinks: Util?,
    val description: String?,
    val publisher: String?,
    var pageCount: Int
) : Parcelable
