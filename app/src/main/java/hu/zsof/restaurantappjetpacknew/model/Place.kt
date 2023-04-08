package hu.zsof.restaurantappjetpacknew.model

import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.model.enums.Type
import java.io.Serializable

data class Place(
    val id: Long = 0,
    var name: String = "",
    var address: String = "",
    var phoneNumber: String? = null,
    var email: String? = null,
    var web: String? = null,
    var type: Type = Type.RESTAURANT,
    var rate: Float = 2.0f,
    var price: Price = Price.LOW,
    var image: String? = null,
    var filter: CustomFilter = CustomFilter(),
    var latitude: Double? = null,
    var longitude: Double? = null,
    var usersNumber: Int = 0,
    var openDetails: OpenDetails = OpenDetails(),
    var creatorName: String = "",
    var creatorId: Long = 0,
) : Serializable
