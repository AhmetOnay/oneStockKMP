package ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Turquoise,
    secondary = TropicalOcean,
    onPrimary = Color.White,
)

private val LightColorPalette = lightColors(
    primary = Turquoise,
    secondary = TropicalOcean,
    onPrimary = Color.White,
)

@Composable
fun OneStockTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
