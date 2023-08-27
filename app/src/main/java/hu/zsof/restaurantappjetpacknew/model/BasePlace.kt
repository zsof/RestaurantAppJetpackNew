package hu.zsof.restaurantappjetpacknew.model

import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.model.enums.Type

open class BasePlace(
    open val id: Long = 0,
    open var name: String = "",
    open var address: String = "",
    open var phoneNumber: String? = null,
    open var email: String? = null,
    open var web: String? = null,
    open var type: Type = Type.RESTAURANT,
    open var rate: Float = 2.0f,
    open var price: Price = Price.LOW,
    open var image: String? = null,
    open var filter: CustomFilter = CustomFilter(),
    open var latitude: Double = 0.0,
    open var longitude: Double = 0.0,
    open var usersNumber: Int = 0,
    open var openDetails: OpenDetails = OpenDetails(),
    open var creatorName: String = "",
    open var creatorId: Long = 0,
    open var isModified: Boolean = false,
)
