package com.example.gotam_project.ui.components

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.gotam_project.R
import java.util.concurrent.TimeUnit

class WalkWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        showNotification()
        return Result.success()
    }

    private fun showNotification() {
        val notification = NotificationCompat.Builder(
            applicationContext,
            "walk_channel"
        )
            .setContentTitle("Прогулка завершена")
            .setContentText("Время прогулки истекло")
            .setSmallIcon(R.drawable.ic_walk)
            .build()

        val manager = applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, notification)
    }

    companion object {
        fun schedule(context: Context, minutes: Int) {
            val workManager = WorkManager.getInstance(context)
            val request = OneTimeWorkRequestBuilder<WalkWorker>()
                .setInitialDelay(minutes.toLong(), TimeUnit.MINUTES)
                .build()
            workManager.enqueue(request)
        }
    }
}