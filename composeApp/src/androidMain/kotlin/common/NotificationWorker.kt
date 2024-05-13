package common

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import api.ApiService
import api.NetworkClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import models.News
import org.example.onestock.R
import repositories.StockNewsRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NotificationWorker (appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val apiService = ApiService(NetworkClient.httpClient)
        val stockNewsRepository = StockNewsRepository(apiService)

        val job = CoroutineScope(Dispatchers.IO).launch {
            try {
                val todayDate = LocalDate.now()
                val formattedDateTime = todayDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "T00:00"
                val news = stockNewsRepository.fetchNewsSynchronously("us", true, 3, formattedDateTime)
                showNotification(news)
            } catch (e: Exception) {
                print(e)
            }
        }
        runBlocking {
            job.join()
        }

        return Result.success()
    }

    private fun showNotification(news: News?) {
        val channelId = "NewsChannel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "News Updates"
            val descriptionText = "Daily news updates"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.mipmap.moneyicon)
            .setContentTitle("Stock Market News")
            .setContentText(news?.data?.get(0)?.title)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        if(ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
            NotificationManagerCompat.from(applicationContext).notify(1, notification)
        }
    }
}