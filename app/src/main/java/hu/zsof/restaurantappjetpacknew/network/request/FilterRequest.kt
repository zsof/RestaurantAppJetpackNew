package hu.zsof.restaurantappjetpacknew.network.request

import hu.zsof.restaurantappjetpacknew.model.CustomFilter
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.model.enums.Type

data class FilterRequest(
    var filter: CustomFilter = CustomFilter(),
    var type: Type? = null,
    val price: Price? = null
)
