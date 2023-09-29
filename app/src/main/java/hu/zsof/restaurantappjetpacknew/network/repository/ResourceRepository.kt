package hu.zsof.restaurantappjetpacknew.network.repository

import hu.zsof.restaurantappjetpacknew.network.ApiService
import hu.zsof.restaurantappjetpacknew.util.recordErrorToFirebase
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ResourceRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun addNewImage(
        filePath: String,
        itemId: Long,
        type: String,
        previousImagePath: String? = null
    ) {
        return try {
            val file = File(filePath)
            val requestFile = file.asRequestBody("file".toMediaTypeOrNull())
            val multipartFile =
                MultipartBody.Part.createFormData(
                    "image",
                    filePath,
                    requestFile,
                )

            apiService.addNewImage(
                multipartFile,
                type,
                itemId.toString(),
                previousImagePath
            )
        } catch (e: Exception) {
            recordErrorToFirebase(e)
        }
    }
}
