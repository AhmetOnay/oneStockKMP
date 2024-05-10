package common

import platform.UIKit.UIApplication
import platform.Foundation.NSURL

actual fun openUrl(url: String) {
    val nsUrl = NSURL(string = url)
    nsUrl?.let { UIApplication.sharedApplication.openURL(it) }
}