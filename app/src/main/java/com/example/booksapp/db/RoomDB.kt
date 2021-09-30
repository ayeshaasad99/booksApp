package com.example.booksapp.db

import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.booksapp.db.BookDao
import com.example.booksapp.model.Book

@Database(version = 4, entities = [Book::class], exportSchema = true)

abstract class RoomDB : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE books_db ADD COLUMN pageCount Int")
            }
        }
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE books_db ADD COLUMN publisher text")
            }
        }
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE book_db RENAME TO books_db")
            }
        }


    }

}
