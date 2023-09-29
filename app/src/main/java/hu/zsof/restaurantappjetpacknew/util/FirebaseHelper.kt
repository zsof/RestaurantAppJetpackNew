package hu.zsof.restaurantappjetpacknew.util

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import retrofit2.HttpException
import timber.log.Timber

fun recordErrorToFirebase(throwable: Throwable) {
    try {
        if (throwable is HttpException) {
            val response = throwable.response()
            if (response != null) {
                val url = response.raw().request.url
                val message = response.message()
                Firebase.crashlytics.recordException(ApiException("$message - $url"))
            } else {
                Firebase.crashlytics.recordException(throwable)
            }
        } else {
            Firebase.crashlytics.recordException(throwable)
        }
    } catch (e: Exception) {
        Timber.e(throwable)
    }
}

class ApiException(message: String) : RuntimeException(message)
