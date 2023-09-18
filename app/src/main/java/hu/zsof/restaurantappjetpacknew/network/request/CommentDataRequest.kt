package hu.zsof.restaurantappjetpacknew.network.request

data class CommentDataRequest(
    val placeId: Long = -1,
    val message: String = ""
)
