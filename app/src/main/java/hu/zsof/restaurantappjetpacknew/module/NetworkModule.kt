package hu.zsof.restaurantappjetpacknew.module

import android.content.Context
import android.os.Handler
import android.os.Looper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.navigation.Navigator
import hu.zsof.restaurantappjetpacknew.navigation.ScreenModel
import hu.zsof.restaurantappjetpacknew.network.ApiService
import hu.zsof.restaurantappjetpacknew.util.Constants
import hu.zsof.restaurantappjetpacknew.util.extension.showToast
import hu.zsof.restaurantappjetpacknew.util.recordErrorToFirebase
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    operator fun invoke(
        @ApplicationContext context: Context,
        navigator: Navigator
    ): ApiService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(AuthInterceptor(context))
            .addInterceptor(ErrorInterceptor(context, navigator))
            .protocols(listOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
            .build()

        val retrofit =
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        return retrofit.create(ApiService::class.java)
    }

    class AuthInterceptor(private val context: Context) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {

            // add ngRok skip header
            var request = chain.request()
            request = request.newBuilder()
                .header("ngrok-skip-browser-warning", "yes").build()

            // add bearer token to requests if user logged in already
            val sharedPreferences =
                context.getSharedPreferences(
                    Constants.Prefs.AUTH_SHARED_PREFERENCES,
                    Context.MODE_PRIVATE,
                )
            val tokenForRequest = sharedPreferences.getString("bearer", "")

            val originalResponse: Response = if (tokenForRequest?.isNotBlank() == true) {
                val authenticatedRequest: Request = request.newBuilder()
                    .header("Authorization", tokenForRequest).build()

                // send to backend
                chain.proceed(authenticatedRequest)
            } else {
                // send to backend without token
                chain.proceed(request)
            }

            // get authorization token from backend when login from response
            val token = originalResponse.headers["Authorization"]
            if (token != null) {
                sharedPreferences.edit().putString("bearer", token).apply()
            }
            return originalResponse
        }
    }

    class ErrorInterceptor(private val context: Context, private val navigator: Navigator) :
        Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalResponse: Response = chain.proceed(chain.request())

            if (!originalResponse.isSuccessful) {
                when (originalResponse.code) {
                    401 -> {
                        val sharedPreferences =
                            context.getSharedPreferences(
                                Constants.Prefs.AUTH_SHARED_PREFERENCES,
                                Context.MODE_PRIVATE,
                            )
                        sharedPreferences.edit().putString("bearer", "").apply()
                        navigator.destination.postValue(ScreenModel.NavigationScreen.Login)
                        recordErrorToFirebase(
                            Exception(
                                originalResponse.message + originalResponse.code
                            )
                        )
                    }

                    418 -> {
                        backgroundThreadToast(
                            context,
                            context.getString(R.string.wrong_authentication)
                        )
                        recordErrorToFirebase(
                            Exception(
                                originalResponse.message + originalResponse.code
                            )
                        )
                    }

                    500 -> {
                        backgroundThreadToast(
                            context,
                            context.getString(R.string.unexcepted_error)
                        )
                        recordErrorToFirebase(
                            Exception(
                                originalResponse.message + originalResponse.code
                            )
                        )
                    }

                    else -> {
                        backgroundThreadToast(
                            context,
                            context.getString(R.string.unexcepted_error)
                        )
                        recordErrorToFirebase(
                            Exception(
                                originalResponse.message + originalResponse.code
                            )
                        )
                    }
                }
            }
            return originalResponse
        }

        private fun backgroundThreadToast(context: Context?, msg: String?) {
            if (context != null && msg != null) {
                Handler(Looper.getMainLooper()).post {
                    showToast(context, msg)
                }
            }
        }
    }
}
