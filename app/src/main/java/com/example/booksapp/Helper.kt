package com.example.booksapp

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class Helper @Inject constructor(private val okHttpClient: OkHttpClient){


    lateinit var response: Response
    suspend  fun getResponseABC(): JSONArray
    {

        val Url = "https://www.googleapis.com/books/v1/volumes?q=9781451648546".toHttpUrlOrNull()!!.newBuilder()
            .addQueryParameter("startIndex", "0")
            .build()

        val request: Request = Request.Builder()
            .url(Url)
            .build()

             //delay(3000)
            response = okHttpClient.newCall(request).execute()


        val jsonData: String? = response.body?.string()
        val Jobject = JSONObject(jsonData)
        val Jarray = Jobject.getJSONArray("items")
        return Jarray


    }
}