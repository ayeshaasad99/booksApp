package com.example.booksapp.hiltmodule

import android.content.Context
import androidx.room.Room
import com.example.booksapp.db.RoomDB.Companion.MIGRATION_1_2
import com.example.booksapp.db.RoomDB.Companion.MIGRATION_2_3
import com.example.booksapp.db.RoomDB.Companion.MIGRATION_3_4
import com.example.booksapp.db.BookDao
import com.example.booksapp.db.RoomDB
import com.example.booksapp.interfaces.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

   @Provides
   @Singleton
   fun provideDatabase(@ApplicationContext app: Context) =
    Room.databaseBuilder(
    app, RoomDB::class.java,
    "word_database"
    ).addMigrations((MIGRATION_3_4),(MIGRATION_2_3) , (MIGRATION_1_2) ).build()

    @Provides
   @Singleton
    fun getAppDao(roomDB: RoomDB): BookDao
    {
        return roomDB.bookDao()
    }

    @Provides
    @Singleton
    fun getRetrofitServiceInstance(retrofit: Retrofit): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }

/*
    @Provides
    fun providesOkhttpInterceptor() : Interceptor {
        return  Interceptor { chain: Interceptor.Chain ->
            val original: Request = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .addHeader("Accept", "Application/JSON")
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }



    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, interceptor: Interceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()

        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.addInterceptor(interceptor)
        okHttpClient.build()
        return okHttpClient.build()
    }*/

@Provides
@Singleton
fun getOkHttpInstance():OkHttpClient
{

    val okHttpClient = OkHttpClient()
    return okHttpClient


}

    @Provides
   @Singleton
   fun getRetroInstance() : Retrofit {

           return Retrofit.Builder()
               .baseUrl("https://www.googleapis.com/books/v1/")
               .addConverterFactory(GsonConverterFactory.create())
               .build()

   }
}


