package hu.zsof.restaurantappjetpacknew.model

import java.io.Serializable

data class CustomFilter(
    var glutenFree: Boolean = false,
    var lactoseFree: Boolean = false,
    var vegetarian: Boolean = false,
    var vegan: Boolean = false,
    var fastFood: Boolean = false,

    var parkingAvailable: Boolean = false,
    var dogFriendly: Boolean = false,
    var familyPlace: Boolean = false,
    var delivery: Boolean = false,
    var creditCard: Boolean = false,
) : Serializable {
    fun convertToMap(): MutableMap<String, Boolean> {
        val map: MutableMap<String, Boolean> = mutableMapOf()
        map["glutenFree"] = glutenFree
        map["lactoseFree"] = lactoseFree
        map["vegetarian"] = vegetarian
        map["vegan"] = vegan
        map["fastFood"] = fastFood
        map["parkingAvailable"] = parkingAvailable
        map["dogFriendly"] = dogFriendly
        map["familyPlace"] = familyPlace
        map["delivery"] = delivery
        map["creditCard"] = creditCard
        return map
    }
}
