package hu.zsof.restaurantappjetpacknew.util.extension

import hu.zsof.restaurantappjetpacknew.util.Constants
import java.util.prefs.Preferences

fun String?.imageUrl(): String {
    println("____IMG URL: $this")
    if (this == null) {
        return ""
    }
    val cookie = Preferences.userRoot().get("bearer", "")
    // return "${Constants.BASE_URL}/images?image=$this&$cookie"
    return "${Constants.BASE_URL}/images?image=$this"
}
