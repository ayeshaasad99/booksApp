package com.example.booksapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.booksapp.model.Book
import com.example.booksapp.databinding.AdapterBookBinding
import com.example.booksapp.interfaces.ItemClickListener

class MainAdapter(private val onBookClickListener: ItemClickListener) :
    RecyclerView.Adapter<MainViewHolder>() {
    var books = mutableListOf<Book>()

    fun setBookList(books: List<Book>) {

        this.books = books.toMutableList()
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterBookBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val book = books[position]
        holder.binding.book = book




        holder.itemView.setOnClickListener() {

            onBookClickListener.onItemClick(book, it)

        }




    }

    override fun getItemCount(): Int {
        return books.size
    }


}

class MainViewHolder(val binding: AdapterBookBinding) : RecyclerView.ViewHolder(binding.root) {

}



