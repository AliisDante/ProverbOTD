package com.lokewt.potd.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "KJV_verses",
    foreignKeys = [
        ForeignKey(
            Book::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("book_id")
        )
    ]
)
data class Verse(
    @PrimaryKey val id: Int?,
    @ColumnInfo(name="book_id") val book: Int?,
    @ColumnInfo(name="chapter") val chapter: Int?,
    @ColumnInfo(name="verse") val verse: Int?,
    @ColumnInfo(name="text") val text: String?
)