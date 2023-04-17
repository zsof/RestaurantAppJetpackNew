package hu.zsof.restaurantappjetpacknew.network.request

import hu.zsof.restaurantappjetpacknew.model.CustomFilter
import hu.zsof.restaurantappjetpacknew.model.OpenDetails
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.model.enums.Type

data class PlaceDataRequest(
    val name: String = "",
    val address: String = "",
    val web: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val type: Type = Type.RESTAURANT,
    val price: Price = Price.LOW,
    val filter: CustomFilter = CustomFilter(),
    val rate: Float = 2.0f,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val openDetails: OpenDetails = OpenDetails(),
)
