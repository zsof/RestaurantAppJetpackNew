package hu.zsof.restaurantappjetpacknew.model

import androidx.room.Entity
import hu.zsof.restaurantappjetpacknew.network.response.PlaceMapResponse

@Entity(tableName = "place")
class Place : BasePlace()

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
