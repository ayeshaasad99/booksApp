package com.example.booksapp.db

import androidx.room.*
import com.example.booksapp.model.Book


@Dao
interface BookDao {

    @Query("SELECT * FROM books_db order by srNo asc ")
    fun getAll(): List<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: List<Book>)

    @Query("DELETE FROM books_db")
    suspend fun deleteAll()

    @Query("DELETE FROM books_db where id== :id ")
    suspend fun delete(id: String)


}