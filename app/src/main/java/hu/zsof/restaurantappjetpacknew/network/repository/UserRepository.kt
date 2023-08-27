package hu.zsof.restaurantappjetpacknew.network.repository

import hu.zsof.restaurantappjetpacknew.database.dao.FavListDao
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.User
import hu.zsof.restaurantappjetpacknew.network.ApiService
import hu.zsof.restaurantappjetpacknew.network.request.UserUpdateProfileRequest
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val favListDao: FavListDao,
) {

    suspend fun getUserProfile(): User {
        return try {
            apiService.getUserProfile()
        } catch (e: Exception) {
            e.printStackTrace()
            User()
        }
    }

    suspend fun updateUserProfile(userUpdateProfileRequest: UserUpdateProfileRequest): User? {
        return try {
            apiService.updateUserProfile(userUpdateProfileRequest)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun addOrRemoveFavPlace(placeId: Long): User? {
        return try {
            apiService.addPlaceToFav(placeId)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getFavPlacesByUser(): List<Place> {
        return try {
            apiService.getFavPlaces()
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    /**
     * Room calls
     */
    suspend fun insertFav(place: Place) = favListDao.insertFav(place)
    suspend fun deleteFav(place: Place) = favListDao.deleteFav(place)
    suspend fun getAllFav(): List<Place> = favListDao.getAllFav()
}
