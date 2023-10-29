package hu.zsof.restaurantappjetpacknew.util.extension

import hu.zsof.restaurantappjetpacknew.util.Constants

fun String?.imageUrl(): String {
    if (this == null) {
        return ""
    }
    val getImageEndpoint = "${Constants.BASE_URL}images?image=$this"
    println("get full IMG URL $getImageEndpoint")
    return getImageEndpoint
}
