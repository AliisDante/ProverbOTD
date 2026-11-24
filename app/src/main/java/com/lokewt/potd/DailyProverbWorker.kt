package com.lokewt.potd

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class DailyProverbWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        DailyProverb(applicationContext).notifyDailyProverb()
        return Result.success()
    }
}