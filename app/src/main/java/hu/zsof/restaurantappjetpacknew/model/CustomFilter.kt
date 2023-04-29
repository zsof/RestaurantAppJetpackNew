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

    fun convertToList(): FilterList {
        return FilterList(
            mutableListOf(
                glutenFree,
                lactoseFree,
                vegetarian,
                vegan,
                fastFood,
                parkingAvailable,
                dogFriendly,
                familyPlace,
                delivery,
                creditCard,
            ),
        )
    }
}

class FilterList(
    val filters: MutableList<Boolean> = mutableListOf(),
) {
    fun compare(compareTo: FilterList): Boolean {
        if (this.filters.size == compareTo.filters.size) {
            for (i in 0 until filters.size) {
                if (this.filters[i]) {
                    if (!compareTo.filters[i]) {
                        return false
                    }
                }
            }
            return true
        } else {
            return false
        }
    }
}
