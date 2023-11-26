package hu.zsof.restaurantappjetpacknew.network.request

import hu.zsof.restaurantappjetpacknew.model.CustomFilter

data class UserUpdateProfileRequest(
    val name: String? = null,
    val password: String? = null,
    val image: String? = null,
    val email: String? = null,
    val filters: CustomFilter = CustomFilter(),
)
