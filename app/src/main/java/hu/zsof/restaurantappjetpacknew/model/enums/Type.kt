package hu.zsof.restaurantappjetpacknew.model.enums

enum class Type {
    // TODO valami paraméter név + type, mert így fast_food kell a spinnerbe is h egyezzen, és a többnyelvűséget sem támogatja
    RESTAURANT, CAFE, PATISSERIE, BAKERY, BAR, FAST_FOOD, EMPTY;

    companion object {

        fun getByName(selectedCategory: String): Type {
            var type: Type = RESTAURANT
            for (t in values()) {
                if (t.toString().equals(selectedCategory, ignoreCase = true)) {
                    type = t
                    break
                }
            }
            return type
        }
    }
}
