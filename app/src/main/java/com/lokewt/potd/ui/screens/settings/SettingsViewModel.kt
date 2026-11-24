package com.lokewt.potd.ui.screens.settings

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.launch
import com.lokewt.potd.DailyProverb
import com.lokewt.potd.DailyProverbWorker
import java.util.concurrent.TimeUnit

val CHANNEL_ID = "daily_proverb"

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    var notifications_enabled by mutableStateOf(false)
    val notificationManager: NotificationManager = application.applicationContext.getSystemService(
        NotificationManager::class.java ) as NotificationManager
    val workManager = WorkManager.getInstance(application.applicationContext)

    fun loadSettings() {
        notifications_enabled = notificationManager.getNotificationChannel(CHANNEL_ID) != null
    }

    fun toggleNotifications() {
        if (notifications_enabled) {
            disableNotifications()
        }
        else {
            enableNotifications()
        }
    }

    fun disableNotifications() {
        notificationManager.deleteNotificationChannel(CHANNEL_ID)
        workManager.cancelUniqueWork("daily_proverb")
        notifications_enabled = false
    }

    fun enableNotifications() {
        val channel = NotificationChannel(CHANNEL_ID, "Daily Proverb", NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = "Offers daily proverbs"
        }

        notificationManager.createNotificationChannel(channel)
        notifications_enabled = true

        viewModelScope.launch {
            DailyProverb(application.applicationContext).notifyDailyProverb()
        }

        val request = PeriodicWorkRequestBuilder<DailyProverbWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(1, TimeUnit.MILLISECONDS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "daily_proverb",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }
}