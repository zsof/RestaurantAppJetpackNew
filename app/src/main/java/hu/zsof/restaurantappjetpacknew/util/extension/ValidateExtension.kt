package hu.zsof.restaurantappjetpacknew.util.extension

import hu.zsof.restaurantappjetpacknew.util.Constants

fun String?.imageUrl(): String {
    println("____IMG URL: $this")
    if (this == null) {
        return ""
    }
    return "${Constants.BASE_URL}/images?image=$this"
}
