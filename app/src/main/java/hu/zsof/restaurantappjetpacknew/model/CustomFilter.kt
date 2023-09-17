package hu.zsof.restaurantappjetpacknew.model

import hu.zsof.restaurantappjetpacknew.R
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
        map[R.string.gluten_free.toString()] = glutenFree
        map[R.string.lactose_free.toString()] = lactoseFree
        map[R.string.vegetarian.toString()] = vegetarian
        map[R.string.vegan.toString()] = vegan
        map[R.string.fast_food.toString()] = fastFood
        map[R.string.parking_available.toString()] = parkingAvailable
        map[R.string.dog_fancier.toString()] = dogFriendly
        map[R.string.family_place.toString()] = familyPlace
        map[R.string.delivery.toString()] = delivery
        map[R.string.credit_card.toString()] = creditCard
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
