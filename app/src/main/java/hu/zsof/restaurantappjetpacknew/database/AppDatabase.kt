package hu.zsof.restaurantappjetpacknew.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.zsof.restaurantappjetpacknew.database.dao.FavListDao
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.converter.FilterConverter
import hu.zsof.restaurantappjetpacknew.model.converter.OpenDetailsConverter

@Database(
    entities = [
        Place::class,
    ],
    version = 3,
    exportSchema = false,
)
@TypeConverters(FilterConverter::class, OpenDetailsConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favListDao(): FavListDao
}
