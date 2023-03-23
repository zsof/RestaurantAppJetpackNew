package hu.zsof.restaurantappjetpacknew.module

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.zsof.restaurantappjetpacknew.BuildConfig
import hu.zsof.restaurantappjetpacknew.network.ApiService
import hu.zsof.restaurantappjetpacknew.util.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
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
    operator fun invoke(/*@ApplicationContext context: Context*/): ApiService {
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
            .addInterceptor(ReceivedCookiesInterceptor())
            .addInterceptor(AddCookiesInterceptor())
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

    class ReceivedCookiesInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val originalResponse: Response = chain.proceed(chain.request())
            if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
                val cookies = originalResponse.headers("Set-Cookie")

                Preferences.userRoot().put("cookie", cookies[0])
                println(cookies[0])
            }
            return originalResponse
        }
    }

    class AddCookiesInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val builder: Request.Builder = chain.request().newBuilder()
            val cookie = Preferences.userRoot().get("cookie", "")
            if (cookie.isNotEmpty()) {
                println("Cookie ->$cookie")
                builder.addHeader("Cookie", cookie)
                // Timber.tag("OkHttp").d("Adding Header: %s", cookie)
            } else {
                println("ERROR: NO COOKIE ADDED")
            }
            // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            return chain.proceed(builder.build())
        }
    }

    class ErrorInterceptor(private val context: Context) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val originalResponse: Response = chain.proceed(chain.request())
            if (!originalResponse.isSuccessful) {
                // To get custom error messages from server
                val errorBodyResponse = originalResponse.body?.string()

                // To show Toast
                val errorBody = errorBodyResponse?.let { JSONObject(it) }
                backgroundThreadToast(context, errorBody?.getString("errorMessage"))

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
