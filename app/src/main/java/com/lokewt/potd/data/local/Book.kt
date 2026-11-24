package com.lokewt.potd.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "KJV_books")
data class Book(
    @PrimaryKey val id: Int?,
    @ColumnInfo(name="name") val name: String?
)
