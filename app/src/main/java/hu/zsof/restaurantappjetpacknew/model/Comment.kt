package hu.zsof.restaurantappjetpacknew.model

data class Comment(
    val id: Long = -1,
    var message: String = "",
    var userId: Long = -1,
    var userName: String = "",
    var createDate: String,
    var placeId: Long = -1
)
