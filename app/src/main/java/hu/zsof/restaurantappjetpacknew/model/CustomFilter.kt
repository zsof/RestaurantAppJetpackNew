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
    fun convertToMap(): MutableMap<Int, Boolean> {
        val map: MutableMap<Int, Boolean> = mutableMapOf()
        map[R.string.gluten_free] = glutenFree
        map[R.string.lactose_free] = lactoseFree
        map[R.string.vegetarian] = vegetarian
        map[R.string.vegan] = vegan
        map[R.string.fast_food] = fastFood
        map[R.string.parking_available] = parkingAvailable
        map[R.string.dog_fancier] = dogFriendly
        map[R.string.family_place] = familyPlace
        map[R.string.delivery] = delivery
        map[R.string.credit_card] = creditCard
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
