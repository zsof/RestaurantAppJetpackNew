package hu.zsof.restaurantappjetpacknew.network.response

import hu.zsof.restaurantappjetpacknew.model.User

data class LoggedUserResponse(
    val isSuccess: Boolean = false,
    val successMessage: String = "",
    val error: String = "",
    val user: User? = null,
)
