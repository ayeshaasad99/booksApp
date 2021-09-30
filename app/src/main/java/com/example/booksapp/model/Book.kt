package com.example.booksapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.Parceler

@Parcelize
@Entity(tableName = "books_db")
data class Book(@PrimaryKey val id: String, @Embedded val volumeInfo: Title?, var srNo: Int) :
    Parcelable {

    private constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readParcelable<Title>(Title::class.java.classLoader),
        parcel.readInt()

    )


    companion object : Parceler<Book> {

        override fun Book.write(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeParcelable(volumeInfo, flags)
            parcel.writeInt(srNo)

        }

        override fun create(parcel: Parcel): Book {
            return Book(parcel)
        }
    }

    fun getID(): String {
        return id
    }


}


