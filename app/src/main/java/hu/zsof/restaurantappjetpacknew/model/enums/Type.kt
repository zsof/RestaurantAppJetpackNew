package hu.zsof.restaurantappjetpacknew.model.enums

enum class Type(name: String) {
    RESTAURANT("Étterem"), CAFE("Kávézó"), PATISSERIE("Cukrászda"), BAKERY("Pékség"), BAR("Bár"), FAST_FOOD("Gyors étterem"), EMPTY("");

    companion object {

        fun getByName(selectedCategory: String): Type {
            var type: Type = RESTAURANT
            for (t in values()) {
                if (t.name.equals(selectedCategory, ignoreCase = true)) {
                    type = t
                    break
                }
            }
            return type
        }
    }
}
