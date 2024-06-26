import android.os.Build
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
