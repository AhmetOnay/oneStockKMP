package common

import api.ApiService
import api.NetworkClient
import repositories.StockNewsRepository
import java.awt.*
import java.awt.TrayIcon.MessageType
import java.awt.image.BufferedImage
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NotificationWorker {
    suspend fun showNotification() {
        val apiService = ApiService(NetworkClient.httpClient)
        val stockNewsRepository = StockNewsRepository(apiService)
        val todayDate = LocalDate.now()
        val formattedDateTime = todayDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "T00:00"
        val news = stockNewsRepository.fetchNewsSynchronously("us", true, 3, formattedDateTime)
        val title = "Stock Market News"
        val message = news?.data?.get(0)?.title
        if (SystemTray.isSupported()) {
            val tray = SystemTray.getSystemTray()
            val imageURL = javaClass.classLoader.getResource("moneyicon.png")
            val image = Toolkit.getDefaultToolkit().getImage(imageURL);
            val trayIcon = TrayIcon(image, "News")
            try {
                tray.add(trayIcon)
                trayIcon.displayMessage(title, message, MessageType.INFO)
            } catch (e: AWTException) {
                println("Trayicon could not be added.")
            }
        } else {
            println("System tray not supported!")
        }
    }
}