package hu.zsof.restaurantappjetpacknew.network

import hu.zsof.restaurantappjetpacknew.model.Comment
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.PlaceInReview
import hu.zsof.restaurantappjetpacknew.model.User
import hu.zsof.restaurantappjetpacknew.network.request.CommentDataRequest
import hu.zsof.restaurantappjetpacknew.network.request.FilterRequest
import hu.zsof.restaurantappjetpacknew.network.request.LoginDataRequest
import hu.zsof.restaurantappjetpacknew.network.request.PlaceDataRequest
import hu.zsof.restaurantappjetpacknew.network.request.UserUpdateProfileRequest
import hu.zsof.restaurantappjetpacknew.network.response.LoggedUserResponse
import hu.zsof.restaurantappjetpacknew.network.response.NetworkResponse
import hu.zsof.restaurantappjetpacknew.network.response.PlaceMapResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    /**
     * Places (role: user, owner, admin)
     */
    @GET("places")
    suspend fun getAllPlace(): List<Place>

    @GET("places/map")
    suspend fun getAllPlaceInMap(): List<PlaceMapResponse>

    @GET("places/{placeId}")
    suspend fun getPlaceById(@Path("placeId") placeId: Long): Place

    @POST("places/filter")
    suspend fun filterPlaces(@Body filter: FilterRequest): List<Place>

    /**
     * Comments (role: user, owner, admin)
     */
    @GET("places/comment/{placeId}")
    suspend fun getCommentsByPlaceId(@Path("placeId") placeId: Long): List<Comment>

    @POST("places/comment")
    suspend fun addNewComment(
        @Body commentDataRequest: CommentDataRequest,
    ): Comment

    @DELETE("places/comment/{id}")
    suspend fun deleteComment(@Path("id") commentId: Long)

    /**
     * Place by Owner (role: owner, admin)
     */

    @POST("places-owner/new-place")
    suspend fun addNewPlace(
        @Body placeDataRequest: PlaceDataRequest,
    ): PlaceInReview

    @DELETE("places-owner/places/{id}")
    suspend fun deletePlace(@Path("id") placeId: Long)

    @DELETE("places-owner/place-review/{id}")
    suspend fun deletePlaceFromInReview(@Path("id") placeId: Long)

    @GET("places-owner/places")
    suspend fun getAllPlaceByOwner(): List<Place>

    @GET("places-owner/places-in-review")
    suspend fun getAllPlaceInReviewByOwner(): List<PlaceInReview>

    @GET("places-owner/{id}")
    suspend fun getPlaceByIdFromInReview(@Path("id") placeId: Long): PlaceInReview

    @POST("places-owner/update")
    suspend fun updatePlace(@Body placeDataRequest: PlaceDataRequest): Place

    /**
     * Place in-review (role: admin)
     */
    @GET("places-review")
    suspend fun getAllPlaceFromInReview(): List<PlaceInReview>

    @GET("places-review/places/modified")
    suspend fun getAllModifiedPlace(): List<Place>

    @POST("places-review/accept/{id}")
    suspend fun acceptPlaceFromInReview(
        @Path("id") placeId: Long,
        @Query("isModifiedPlace") isModifiedPlace: Boolean,
    )

    @POST("places-review/report/{id}")
    suspend fun reportProblemPlaceInReview(
        @Path("id") placeId: Long,
        @Body problemMessage: String,
        @Query("isModifiedPlace") isModifiedPlace: Boolean,
    )

    /**
     * Image
     */
    @Multipart
    @POST("images")
    suspend fun addNewImage(
        @Part file: MultipartBody.Part,
        @Part("type") type: String,
        @Part("itemId") typeId: String,
        @Part("previous-image") previousImagePath: String?,
    )

    /**
     * Auth
     */
    @POST("auth/register")
    suspend fun registerUser(
        @Body loginDataRequest: LoginDataRequest,
        @Header("isAdmin") isAdmin: Boolean,
        @Header("isOwner") isOwner: Boolean,
    ): NetworkResponse

    @POST("auth/login")
    suspend fun loginUser(@Header("Authorization") encodedBasic: String): LoggedUserResponse

    @GET("auth")
    suspend fun authUser(): LoggedUserResponse

    /**
     * Admin
     */
    @GET("admin/users/{id}")
    suspend fun getUserById(@Path("id") userId: Long): User

    @GET("admin/users")
    suspend fun getAllUser(): List<User>

    @DELETE("admin/users/{id}")
    suspend fun deleteUserById(@Path("id") userId: Long)

    /**
     * User
     */
    @GET("users/profile")
    suspend fun getUserProfile(): User

    @PUT("users/profile")
    suspend fun updateUserProfile(@Body userUpdateProfileRequest: UserUpdateProfileRequest): User

    @POST("users/fav-places/{placeId}")
    suspend fun addPlaceToFav(@Path("placeId") placeId: Long): User

    @GET("users/fav-places")
    suspend fun getFavPlaces(): List<Place>
}
