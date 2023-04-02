package hu.zsof.restaurantappjetpacknew.module

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.zsof.restaurantappjetpacknew.BuildConfig
import hu.zsof.restaurantappjetpacknew.network.ApiService
import hu.zsof.restaurantappjetpacknew.util.Constants
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.prefs.Preferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InjectModule {

    /* @Singleton
     @Provides
     fun provideStateService(): LocalDataStateService {
         return LocalDataStateService
     }*/

    @Singleton
    @Provides
    operator fun invoke(
        @ApplicationContext context: Context,
    ): ApiService {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(AuthInterceptor())
           // .addInterceptor(ErrorInterceptor(context))
            .build()

        val retrofit =
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        return retrofit.create(ApiService::class.java)
    }

    class AuthInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            // add bearer token to requests if user logged in already
            val tokenForRequest = Preferences.userRoot().get("bearer", "")
            val originalResponse: Response

            if (tokenForRequest.isNotBlank()) {
                val request = chain.request()
                val authenticatedRequest: Request = request.newBuilder()
                    .header("Authorization", tokenForRequest).build()

                // send to backend
                originalResponse = chain.proceed(authenticatedRequest)
            } else {
                // send to backend without token
                originalResponse = chain.proceed(chain.request())
            }

            // get authorization token from backend when login from response
            val token = originalResponse.headers["Authorization"]
            if (token != null) {
                Preferences.userRoot().put("bearer", token)
            }
            return originalResponse
        }
    }

    class ErrorInterceptor(private val context: Context) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val originalResponse: Response = chain.proceed(chain.request())
            if (!originalResponse.isSuccessful) {
                // To get custom error messages from server
                val errorBodyResponse = originalResponse.body?.string()

                println("errorbodyresp $errorBodyResponse")
                // To show Toast
                val errorBody = errorBodyResponse?.let { JSONObject(it) }
                println("errorbody $errorBody")
                backgroundThreadToast(context, errorBody?.getString("message"))

                // To not crash
                val body = errorBodyResponse?.toResponseBody(originalResponse.body?.contentType())

                return originalResponse.newBuilder().body(body).build()
            }
            return originalResponse
        }

        private fun backgroundThreadToast(context: Context?, msg: String?) {
            if (context != null && msg != null) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(
                        context,
                        msg,
                        Toast.LENGTH_LONG,
                    ).show()
                }
            }
        }
    }
}
