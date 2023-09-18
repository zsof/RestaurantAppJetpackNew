package hu.zsof.restaurantappjetpacknew.network.repository

import hu.zsof.restaurantappjetpacknew.network.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ResourceRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun addNewImage(
        file: File, //filePath: String
        itemId: Long,
        type: String,
    ) {
        return try {
            //val file = File(filePath)
            val requestFile = file.asRequestBody("file".toMediaTypeOrNull())
            val multipartFile =
                MultipartBody.Part.createFormData(
                    "image",
                    file.name,
                    requestFile,
                )

            println("kép ${multipartFile.body}")
            apiService.addNewImage(
                multipartFile,
                type,
                itemId.toString(),
            )
        } catch (e: Exception) {
            println("körte ${e.message}")
            e.printStackTrace()
        }
    }
}
