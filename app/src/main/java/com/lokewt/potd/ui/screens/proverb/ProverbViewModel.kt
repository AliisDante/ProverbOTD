package com.lokewt.potd.ui.screens.proverb

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lokewt.potd.data.local.Verse
import com.lokewt.potd.data.local.VerseDatabase
import com.lokewt.potd.data.repository.VerseRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.application
import com.lokewt.potd.DailyProverb

class ProverbViewModel(application: Application) : AndroidViewModel(application) {
    private val database = VerseDatabase.getDatabase(application)
    private val repository = VerseRepository(database.verseDao())
    var verse by mutableStateOf<Verse?>(null)

    fun loadVerse() {
        viewModelScope.launch {
            // Seed random with today's time
            verse = DailyProverb(application.applicationContext).getDailyProverb()
        }
    }

    fun refreshVerse() {
        viewModelScope.launch {
            val chapter_select = (1..31).random()
            val verse_select = (1..repository.getVersesLength(chapter_select)).random()
            verse = repository.getProverbByChapterVerse(chapter_select, verse_select)
        }
    }
}