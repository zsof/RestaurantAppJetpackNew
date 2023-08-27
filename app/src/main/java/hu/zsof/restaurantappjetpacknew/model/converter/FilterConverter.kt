package hu.zsof.restaurantappjetpacknew.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import hu.zsof.restaurantappjetpacknew.model.CustomFilter

class FilterConverter {
    @TypeConverter
    fun fromJson(value: String): CustomFilter = Gson().fromJson(value, CustomFilter::class.java)

    @TypeConverter
    fun toJson(value: CustomFilter): String = Gson().toJson(value)
}
