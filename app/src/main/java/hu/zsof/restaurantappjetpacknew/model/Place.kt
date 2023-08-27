package hu.zsof.restaurantappjetpacknew.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.zsof.restaurantappjetpacknew.network.response.PlaceMapResponse

/*data class Place(
    override val id: Long = 0,
    override var name: String = "",
    override var address: String = "",
    override var phoneNumber: String? = null,
    override var email: String? = null,
    override var web: String? = null,
    override var type: Type = Type.RESTAURANT,
    override var rate: Float = 2.0f,
    override var price: Price = Price.LOW,
    override var image: String? = null,
    override var filter: CustomFilter = CustomFilter(),
    override var latitude: Double = 0.0,
    override var longitude: Double = 0.0,
    override var usersNumber: Int = 0,
    override var openDetails: OpenDetails = OpenDetails(),
    override var creatorName: String = "",
    override var creatorId: Long = 0,
) : BasePlace()*/

@Entity(tableName = "place")
data class Place(
    @PrimaryKey
    override val id: Long,
) : BasePlace()

fun Place.convertToPlaceMapResponse(): PlaceMapResponse {
    return PlaceMapResponse(
        this.id,
        this.name,
        this.address,
        this.latitude,
        this.longitude,
        this.filter,
    )
}

fun List<Place>.convertToPlaceMapResponse(): MutableList<PlaceMapResponse> {
    val placeMapResponses = mutableListOf<PlaceMapResponse>()
    this.forEach {
        placeMapResponses.add(it.convertToPlaceMapResponse())
    }
    return placeMapResponses
}
