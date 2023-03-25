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
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
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
            .addInterceptor(BasicAuthInterceptor("test@test.hu", "Alma1234"))
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

    // TODO
    class ReceivedCookiesInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val originalResponse: Response = chain.proceed(chain.request())
            println("originalresp $originalResponse")
            if (originalResponse.headers("Authorization").isNotEmpty()) {
                val token = originalResponse.headers("Authorization")

                Preferences.userRoot().put("bearer", token[0])
                println("token ${token[0]}")
            }
            return originalResponse
        }
    }

    // todo
    class BasicAuthInterceptor(username: String, password: String) : Interceptor {
        private val credentials: String = Credentials.basic(username, password)

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            val authenticatedRequest: Request = request.newBuilder()
                .header("Authorization", credentials).build()
            if (authenticatedRequest.headers("Authorization").isNotEmpty()) {
                val token = authenticatedRequest.headers("Authorization")

                println("token $token")
                Preferences.userRoot().put("bearer", token[0])
            }
            println("auht $authenticatedRequest")
            return chain.proceed(authenticatedRequest)
        }
       /* @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            val authenticatedRequest: Request = request.newBuilder()
                .header("Authorization", credentials).build()
            if (authenticatedRequest.headers("Authorization").isNotEmpty()) {
                val token = authenticatedRequest.headers("Authorization")

                println("token $token")
                Preferences.userRoot().put("bearer", token[0])
            }
            println("auht $authenticatedRequest")
            return chain.proceed(authenticatedRequest)
        }*/
    }

    class AddCookiesInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val builder: Request.Builder = chain.request().newBuilder()
            val token = Preferences.userRoot().get("bearer", "")
            if (token.isNotEmpty()) {
                println("Bearer ->$token")
                builder.addHeader("Authorization", "Bearer $token")
                // Timber.tag("OkHttp").d("Adding Header: %s", cookie)
            } else {
                println("ERROR: NO TOKEN ADDED")
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
