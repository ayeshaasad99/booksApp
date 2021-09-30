package com.example.booksapp.interfaces

import com.example.booksapp.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("volumes?q=9781451648546")
    suspend fun getAllBooks(
        @Query("startIndex")
        startIndex: Int? = 0
    ) : Response

}