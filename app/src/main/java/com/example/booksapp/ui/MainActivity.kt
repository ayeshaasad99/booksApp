package com.example.booksapp.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.example.booksapp.databinding.ActivityMainBinding
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.view.View
import android.widget.AbsListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.adapters.AdapterViewBindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booksapp.helper.Constants.Companion.QUERY_PAGE_SIZE
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.booksapp.*
import com.example.booksapp.helper.Status
import com.example.booksapp.helper.Utility
import com.example.booksapp.interfaces.ItemClickListener
import com.example.booksapp.model.Book
import com.example.booksapp.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ItemClickListener {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter:MainAdapter
    var isLastPage = false
    lateinit var viewModel: MainViewModel


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Log.d("abc", "CREATE ACTIVITY")



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniRefreshListener()

        adapter = MainAdapter(this)
        binding.recyclerview.adapter = adapter

        binding.recyclerview.apply {

            addOnScrollListener(this@MainActivity.scrollListener)

        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.bookList.observe(this, Observer {

            Log.d(TAG, "onCreate: $it")

            adapter.setBookList(it)

            val totalPages = 927 / QUERY_PAGE_SIZE + 2
            isLastPage = viewModel.pg == totalPages


        })

        viewModel.errorMessage.observe(this, Observer {
        })



        viewModel.status.observe(this, Observer {
            when (it) {

                Status.Success -> {
                    hideProgressBar()

                }

                Status.Loading -> {
                    showProgressBar()
                }


            }


        })


        Utility.getInstance().setContext(this)
        viewModel.getAllBooks()


    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent: Intent? = result.data

                val index = intent?.getIntExtra("index", 0)
                Log.d("me1","$index")

                if (index != null) {
                    adapter.books.removeAt(index)
                    adapter.notifyItemRemoved(index)

                }
            }
        }

    override fun onStart() {
        super.onStart()
        Log.d("abc1", "START ACTIVITY")
    }

    override fun onResume() {
        super.onResume()
        Log.d("abc2", "RESUME ACTIVITY")
    }

    override fun onPause() {
        super.onPause()
        Log.d("abc3", "PAUSE ACTIVITY")
    }

    override fun onStop() {
        super.onStop()
        Log.d("abc4", "STOP ACTIVITY")

    }

    override fun onRestart() {
        super.onRestart()
        Log.d("abc5", "RESTART ACTIVITY")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("abc6", "DESTROY ACTIVITY")
    }

    private fun hideProgressBar() {

        binding.progressBar.visibility = View.INVISIBLE
        isLoading = false
    }


    private fun showProgressBar() {

        binding.progressBar.visibility = View.VISIBLE
        isLoading = true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun iniRefreshListener() {
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { // This method gets called when user pull for refresh,
            viewModel.getAllBooks()
            val handler = Handler()
            handler.postDelayed(Runnable {
                if (swipeRefreshLayout.isRefreshing) {
                    swipeRefreshLayout.isRefreshing = false
                }
            }, 1000)
        })
    }


    var isLoading = false

    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getAllBooksOnScroll()
                isScrolling = false
            } else {
                binding.recyclerview.setPadding(0, 0, 0, 0)
            }


        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }


    }


    override fun onItemClick(book: Book?, view: View) {
        val intent = Intent(this, DetailViewActivity::class.java)
        intent.putExtra("Key", book)
        val options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "transition_image")
        /*startActivity(intent, options.toBundle())*/
        resultLauncher.launch(intent,options)

    }



}






