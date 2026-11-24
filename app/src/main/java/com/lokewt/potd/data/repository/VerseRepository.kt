package com.lokewt.potd.data.repository

import com.lokewt.potd.data.local.Verse
import com.lokewt.potd.data.local.VerseDao

class VerseRepository(private val verseDao: VerseDao) {
    suspend fun getAllProverbs(): List<Verse> = verseDao.getAllProverbs()
    suspend fun getProverbByChapterVerse(c: Int, v: Int): Verse = verseDao.getProverbByChapterVerse(c, v)
    suspend fun getVersesLength(c: Int): Int = verseDao.getVersesLength(c)
}