package hu.zsof.restaurantappjetpacknew.model.enums

enum class Type(val nameValue: String) {
    RESTAURANT("Étterem"), CAFE("Kávézó"), PATISSERIE("Cukrászda"), BAKERY("Pékség"), BAR("Bár"), FAST_FOOD("Gyors étterem"), EMPTY("");

    companion object {

        fun getByName(selectedCategory: String): Type {
            return Type.values().find{ it.nameValue == selectedCategory } ?: RESTAURANT
        }

        fun getByType(selectedCategory: Type): String {
            return Type.values().find { it == selectedCategory }?.nameValue ?: "Étterem"
        }
    }
}
