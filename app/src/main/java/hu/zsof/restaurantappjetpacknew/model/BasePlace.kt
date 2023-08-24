package hu.zsof.restaurantappjetpacknew.model

import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.model.enums.Type

open class BasePlace(
    open val id: Long = 0,
    open val name: String = "",
    open val address: String = "",
    open val phoneNumber: String? = null,
    open val email: String? = null,
    open val web: String? = null,
    open val type: Type = Type.RESTAURANT,
    open val rate: Float = 2.0f,
    open val price: Price = Price.LOW,
    open val image: String? = null,
    open val filter: CustomFilter = CustomFilter(),
    open val latitude: Double = 0.0,
    open val longitude: Double = 0.0,
    open val usersNumber: Int = 0,
    open val openDetails: OpenDetails = OpenDetails(),
    open val creatorName: String = "",
    open val creatorId: Long = 0,
    open val isModified: Boolean = false,
)
