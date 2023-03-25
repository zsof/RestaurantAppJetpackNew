package hu.zsof.restaurantappjetpacknew.model

import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.model.enums.Type
import java.io.Serializable

data class PlaceInReview(
    val id: Long = 0,
    var name: String = "",
    var address: String = "",
    var phoneNumber: String? = "",
    var email: String? = "",
    var web: String? = "",
    var type: Type = Type.RESTAURANT,
    var rate: Float = 2.0f,
    var price: Price = Price.LOW,
    var image: String? = "",
    var filter: CustomFilter = CustomFilter(),
    var latitude: Double? = 0.0,
    var longitude: Double? = 0.0,
    var usersNumber: Int = 0,
    var openDetails: OpenDetails = OpenDetails(),
    var problem: String = "",
) : Serializable
