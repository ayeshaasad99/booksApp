package com.example.booksapp.helper

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

class Utility private constructor() {


    private lateinit var context: Context

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var instance: Utility? = null

        fun getInstance(): Utility {
            if (instance == null) {
                instance = Utility()
            }
            return instance as Utility
        }

    }

    fun setContext(con: Context) {
        context = con
    }

    fun getContext(): Context {
        return context
    }


}