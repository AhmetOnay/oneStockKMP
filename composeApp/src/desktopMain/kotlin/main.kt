import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import common.NotificationWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalTime
import java.time.ZoneId
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

fun main() = application {
    scheduleDailyNotification()
    Window(
        onCloseRequest = ::exitApplication,
        title = "oneStockKMP",
    ) {
        App()
    }
}

fun scheduleDailyNotification() {
    val scheduler = Executors.newScheduledThreadPool(1)
    val task = Runnable {
        GlobalScope.launch(Dispatchers.IO) {
            val notificationWorker = NotificationWorker()
            notificationWorker.showNotification()
        }
    }

    val now = LocalTime.now(ZoneId.systemDefault())
    val nextRun = LocalTime.of(14, 33)
    val initialDelay = if (now.isBefore(nextRun))
        Duration.between(now, nextRun).toMillis()
    else
        Duration.between(now, nextRun.plusHours(24)).toMillis()

    scheduler.scheduleAtFixedRate(task, initialDelay, TimeUnit.DAYS.toMillis(1), TimeUnit.MILLISECONDS)
}