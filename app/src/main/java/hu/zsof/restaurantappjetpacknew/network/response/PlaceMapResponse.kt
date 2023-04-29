package hu.zsof.restaurantappjetpacknew.network.response

import hu.zsof.restaurantappjetpacknew.model.CustomFilter

data class PlaceMapResponse(
    val id: Long = -1,
    var name: String = "",
    var address: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var filterItems: CustomFilter = CustomFilter(),
)
