package hu.zsof.restaurantappjetpacknew.database

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.zsof.restaurantappjetpacknew.database.dao.FavListDao
import hu.zsof.restaurantappjetpacknew.model.Place

@Database(
    entities = [
        Place::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favListDao(): FavListDao
}
