package hu.zsof.restaurantappjetpacknew.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xff03a9f4), // gombok alapháttere
    onPrimary = Color(0xFF001240),
    primaryContainer = Color(0xff03a9f4),
    onPrimaryContainer = Color(0xFFFFFFFF),
    secondary = Color(0xFF001240), // ??
    onSecondary = Color(0xFFFFFFFF), // ??
    secondaryContainer = Color(0xff03a9f4), // bottonbaron az ikonok háttere,
    onSecondaryContainer = Color(0xFFFFFFFF), // bottonbaron az ikon körvonala
    tertiary = Color(0xFFCA0C0C), // ??
    onTertiary = Color(0xFFCA0C0C), // ??
    tertiaryContainer = Color(0xFFCA0C0C), // ??
    onTertiaryContainer = Color(0xFFCA0C0C), // ??
    error = Color(0xFFCA0C0C),
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,
    background = Color(0xFF000000), // ez csak akkor ha Surface van
    // onBackground = Color(0xFF000000),
    surface = Color(0xFF2D2D2E), // Login háttere, dialog háttere
    outline = Color(0xFFFFFFFF), // button, textfieldek körvonala
    surfaceVariant = Color(0xFF2D2D2E), // card színe!!,  slideren a vonal, görgetős háttere
    onSurfaceVariant = Color(0xFFFFFFFF), // iconok színe, hint-ek színe, checkob színe
)

private val LightColorScheme = lightColorScheme(

    /* primary = PBrown,
     onPrimary = PLight,
     secondary = SBrown,
     onSecondary = SLight,*/

    primary = Color(0xFFFFC107),
    onPrimary = OnPrimaryLight,
    primaryContainer = Color(0xFFFFC107),
    onPrimaryContainer = OnPrimaryContainerLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = Color(0xFF1DFFFF),
    onSecondaryContainer = OnSecondaryContainerLight,
    tertiary = TertiaryLight,
    onTertiary = OnTertiaryLight,
    tertiaryContainer = TertiaryContainerLight,
    onTertiaryContainer = OnTertiaryContainerLight,
    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,
    background = Color(0xFFE7E6E6),
    onBackground = Color(0xD8705D5D),
    surface = BackgroundLight,
    outline = OutlineLight,
    surfaceVariant = Color(0xFFFFFFFF),
    onSurfaceVariant = Color(0xD8030303),
)

@Composable
fun RestaurantAppJetpackNewTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content,
    )
}
