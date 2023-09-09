package hu.zsof.restaurantappjetpacknew.model

import hu.zsof.restaurantappjetpacknew.util.Constants.ROLE_USER

data class User(
    val id: Long = 0,
    var name: String = "",
    val nickName: String? = null,
    val email: String = "",
    val image: String? = null,
    val userType: String = ROLE_USER,
    val favPlaceIds: MutableList<Long> = mutableListOf(),
    val filterItems: CustomFilter = CustomFilter(),
)
