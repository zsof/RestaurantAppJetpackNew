package hu.zsof.restaurantappjetpacknew.model

import android.os.Parcelable
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import hu.zsof.restaurantappjetpacknew.model.converter.FilterConverter
import hu.zsof.restaurantappjetpacknew.model.converter.OpenDetailsConverter
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.model.enums.Type
import kotlinx.parcelize.Parcelize

@Parcelize
open class BasePlace(
    @PrimaryKey
    open var id: Long = 0,
    open var name: String = "",
    open var address: String = "",
    open var phoneNumber: String? = null,
    open var email: String? = null,
    open var web: String? = null,
    open var type: Type = Type.RESTAURANT,
    open var rate: Float = 2.0f,
    open var price: Price = Price.LOW,
    open var image: String? = null,
    @TypeConverters(FilterConverter::class)
    open var filter: CustomFilter = CustomFilter(),
    open var latitude: Double = 0.0,
    open var longitude: Double = 0.0,
    open var usersNumber: Int = 0,
    @TypeConverters(OpenDetailsConverter::class)
    open var openDetails: OpenDetails = OpenDetails(),
    open var creatorName: String = "",
    open var creatorId: Long = 0,
    open var isModified: Boolean = false,
    open var problem: String? = null,
): Parcelable
