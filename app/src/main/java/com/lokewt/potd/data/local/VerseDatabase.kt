package com.lokewt.potd.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Verse::class, Book::class], version = 1)
abstract class VerseDatabase : RoomDatabase() {
    abstract fun verseDao(): VerseDao

    companion object {
        @Volatile
        private var INSTANCE: VerseDatabase? = null

        fun getDatabase(context: Context): VerseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VerseDatabase::class.java,
                    "bible.db"
                )
                    .createFromAsset("databases/KJV.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}