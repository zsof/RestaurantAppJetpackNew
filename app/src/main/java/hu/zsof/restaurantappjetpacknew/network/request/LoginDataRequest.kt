package hu.zsof.restaurantappjetpacknew.network.request

data class LoginDataRequest(
    val email: String,
    val password: String,
    val name: String? = null,
    val nickName: String? = null,
)
