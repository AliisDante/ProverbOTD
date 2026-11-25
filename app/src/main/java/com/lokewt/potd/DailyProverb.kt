package com.lokewt.potd

import android.app.PendingIntent
import com.lokewt.potd.data.local.VerseDatabase
import com.lokewt.potd.data.repository.VerseRepository
import java.lang.Math.floorDiv
import kotlin.random.Random
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lokewt.potd.data.local.Verse

class DailyProverb(val context: Context) {
    private val database: VerseDatabase = VerseDatabase.getDatabase(context)
    private val repository: VerseRepository = VerseRepository(database.verseDao())

    // Seed random with today's time
    private val seed: Long = floorDiv(System.currentTimeMillis(), 86400000)

    suspend fun getDailyProverb() : Verse {
        val chapter_select = Random(seed).nextInt(1, 31)
        val verse_select = Random(seed).nextInt(1, repository.getVersesLength(chapter_select))
        val verse = repository.getProverbByChapterVerse(chapter_select, verse_select)
        return verse
    }

    suspend fun notifyDailyProverb() {
        val verse = this.getDailyProverb()
        val notif_text = "${verse.text} - Proverbs ${verse.chapter}:${verse.verse}"

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, "daily_proverb")
            .setStyle(NotificationCompat.BigTextStyle().bigText(notif_text))
            .setSmallIcon(R.drawable.ic_bible)
            .setContentTitle("Daily Proverb")
            .setContentText(notif_text)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            {
                return@with
            }
            notify(seed.toInt(), builder.build())
        }
    }
}