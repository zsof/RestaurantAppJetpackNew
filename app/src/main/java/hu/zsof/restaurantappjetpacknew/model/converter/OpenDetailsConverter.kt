package hu.zsof.restaurantappjetpacknew.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import hu.zsof.restaurantappjetpacknew.model.OpenDetails

class OpenDetailsConverter {
    @TypeConverter
    fun fromJson(value: String): OpenDetails = Gson().fromJson(value, OpenDetails::class.java)

    @TypeConverter
    fun toJson(value: OpenDetails): String = Gson().toJson(value)
}
