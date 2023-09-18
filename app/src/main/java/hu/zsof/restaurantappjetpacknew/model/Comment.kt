package hu.zsof.restaurantappjetpacknew.model

import java.time.Instant

data class Comment(
    val id: Long = -1,
    var message: String = "",
    var userId: Long = -1,
    var userName: String = "",
    var createDate: Instant,
    var placeId: Long = -1
)
