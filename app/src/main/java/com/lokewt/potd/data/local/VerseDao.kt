package com.lokewt.potd.data.local

import androidx.room.Dao
import androidx.room.Query

@Dao
interface VerseDao {
    @Query("SELECT * FROM KJV_verses WHERE book_id = 20")
    suspend fun getAllProverbs(): List<Verse>

    @Query("SELECT * FROM KJV_verses WHERE book_id = 20 AND chapter=:c AND verse=:v")
    suspend fun getProverbByChapterVerse(c: Int, v: Int): Verse

    @Query("SELECT MAX(verse) FROM KJV_verses WHERE book_id = 20 AND chapter=:c")
    suspend fun getVersesLength(c: Int): Int
}