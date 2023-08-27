package hu.zsof.restaurantappjetpacknew.database.dao

import androidx.room.*
import hu.zsof.restaurantappjetpacknew.model.Place

@Dao
interface FavListDao {

    @Query("SELECT * FROM place")
    suspend fun getAllFav(): List<Place>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFav(place: Place)

    @Delete
    suspend fun deleteFav(place: Place)
}
