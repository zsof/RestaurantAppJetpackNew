package hu.zsof.restaurantappjetpacknew.network.response

data class NetworkResponse(
    val isSuccess: Boolean = false,
    val successMessage: String = "",
    val error: String = "",
)
