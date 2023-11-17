package hu.zsof.restaurantappjetpacknew.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark, // gombok alapháttere
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    secondary = SecondaryDark, // tab bar-nak a színe
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark, // bottonbaron az ikonok háttere,
    onSecondaryContainer = OnSecondaryContainerDark, // bottonbaron az ikon körvonala
    tertiary = TertiaryDark,
    onTertiary = OnTertiaryDark,
    tertiaryContainer = TertiaryContainerDark,
    onTertiaryContainer = OnTertiaryContainerDark,
    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,
    background = BackgroundDark, // ez csak akkor ha Scaffold van
    // onBackground = OnBackgroundDark,
    surface = SurfaceDark, // ha surface-ben a content-ben van --> a háttér
    outline = OutlineDark, // button, textfieldek körvonala
    surfaceVariant = SurfaceVariantDark, // card színe!!,  slideren a vonal, görgetős háttere
    onSurfaceVariant = OnSurfaceVariantDark, // iconok színe, hint-ek színe, checkob színe, egyéb szövegek színe
)

private val LightColorScheme = lightColorScheme(

    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,
    tertiary = TertiaryLight,
    onTertiary = OnTertiaryLight,
    tertiaryContainer = TertiaryContainerLight,
    onTertiaryContainer = OnTertiaryContainerLight,
    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    outline = OutlineLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
)

@Composable
fun RestaurantAppJetpackNewTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    val systemUiController = rememberSystemUiController()

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

    systemUiController.setSystemBarsColor(
        color = colors.primary,
    )
    systemUiController.setNavigationBarColor(
        color = colors.secondaryContainer,
    )
}
