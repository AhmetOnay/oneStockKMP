package common

actual fun openUrl(url: String) {
    val window = kotlinx.browser.window
    window.open(url)
}