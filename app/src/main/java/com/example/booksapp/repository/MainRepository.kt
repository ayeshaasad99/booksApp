package com.example.booksapp.repository

import androidx.annotation.WorkerThread
import com.example.booksapp.Helper
import com.example.booksapp.db.BookDao
import com.example.booksapp.interfaces.RetrofitService
import com.example.booksapp.model.Book
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.ArrayList
import javax.inject.Inject
import org.json.JSONObject
import org.json.JSONArray
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull


class MainRepository @Inject constructor(private val retrofitService: RetrofitService, private val dbDao: BookDao, private val helper: Helper, private val okHttpClient: OkHttpClient)

{

    suspend fun getAllBooks(pageNumber: Int?): ArrayList<Book> {

        val response = retrofitService.getAllBooks(pageNumber)
        return response.items

    }



    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(book: List<Book>) =
       dbDao.insert(book)


     fun getAll()=
        dbDao.getAll()

    suspend fun deleteBooks()=
        dbDao.deleteAll()

    suspend fun delete(id:String)
    {
        dbDao.delete(id)
    }

  /*  suspend fun getResponse():JSONArray
    {
       return helper.getResponse()
    }*/

  suspend fun getResponseABC() : JSONArray
    {

        val Url = "https://www.googleapis.com/books/v1/volumes?q=9781451648546".toHttpUrlOrNull()!!.newBuilder()
            .addQueryParameter("startIndex", "0")
            .build()

        val request: Request = Request.Builder()
            .url(Url)
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val jsonData: String? = response.body?.string()
        val Jobject = JSONObject(jsonData)
        val Jarray = Jobject.getJSONArray("items")
        return Jarray

    }



}





