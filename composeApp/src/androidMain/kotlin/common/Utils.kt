package common

import android.content.Context
import android.content.Intent
import android.net.Uri

actual fun openUrl(url: String) {
    val context = ApplicationContextProvider.context
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
}
