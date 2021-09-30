package com.example.booksapp.viewModel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksapp.repository.MainRepository
import com.example.booksapp.helper.Status
import com.example.booksapp.helper.Utility
import com.example.booksapp.model.Book
import com.example.booksapp.model.Util
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import org.json.JSONArray


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val bookList = MutableLiveData<List<Book>>()
    var status = MutableLiveData<Status>()
    val errorMessage = MutableLiveData<String>()
    private var list1 = ArrayList<Book>()
    private var list = ArrayList<Book>()
    var pg: Int = 1
    var count: Int = 0
    val gson = Gson()

    fun getAllBooksOnScroll() = viewModelScope.launch(Dispatchers.IO) {


        status.postValue(Status.Loading)
        // list.addAll(repository.getAllBooks(count))  //API call
        val jArray = repository.getResponseABC()

        for (i in 0 until jArray.length()) {
            // list.addAll(jArray.getJSONObject(i))
            var bookModel = gson.fromJson(jArray[i].toString(), Book::class.java)
            list.add(bookModel)
        }


        for (i in count..count + 9) {
            list[i].srNo = i
        }
        count += 10

        //insert DB
        insert((list ?: ArrayList()) as ArrayList<Book>)
        bookList.postValue(list)
        status.postValue(Status.Success)


    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getAllBooks() = viewModelScope.launch(Dispatchers.IO) {


        //Db Call
        list = repository.getAll() as ArrayList<Book>




        if (list.isNotEmpty()) {
            status.postValue(Status.Loading)
            bookList.postValue(list)

            //API call
            if (isOnline(Utility.getInstance().getContext())) {
                //   list = repository.getAllBooks(0)

                val jArray = repository.getResponseABC()
                for (i in 0 until jArray.length()) {
                    // list.addAll(jArray.getJSONObject(i))
                    var bookModel = gson.fromJson(jArray[i].toString(), Book::class.java)
                    list.add(bookModel)

                    // var book=Books(jArray)
                    //  val jsonObject =  jArray.getJSONObject(i)
                    /*  val id = jsonObject.optString("id")
                      val name = jsonObject.optString("volumeInfo")
                      var book=Book(id,name)*/
                }


                bookList.postValue(list)
            }

            count = list.size

            status.postValue(Status.Success)

        }

        //Empty DB, First API call
        else {

            status.postValue(Status.Loading)

            //API call
            try {
                //list.addAll(repository.getAllBooks(count))
                val jArray = repository.getResponseABC()
                for (i in 0 until jArray.length()) {
                    // list.addAll(jArray.getJSONObject(i))
                    var bookModel = gson.fromJson(jArray[i].toString(), Book::class.java)
                    list.add(bookModel)
                }

            } catch (e: Exception) {
                Log.d("tag", "Exception")
            }





            for (i in 0..list.size - 1) {
                list[i].srNo = i
            }

            count = +10
            bookList.postValue(list)
            insert((list ?: ArrayList()) as ArrayList<Book>)  //insert DB
            status.postValue(Status.Success)

        }


    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }


    private fun insert(book: ArrayList<Book>) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(book)
    }

    /* private fun getAll() = repository.getAll()*/


    fun delete(id: String) = viewModelScope.launch(Dispatchers.IO) {

        repository.delete(id)
    }


}