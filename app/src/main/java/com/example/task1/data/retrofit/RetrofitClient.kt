//import android.content.Context
//import android.util.Log
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//object RetrofitClient {
//    private const val BASE_URL = "https://noknok-staging-api.noknokgroceries.com/"
//
//
//    private var retrofit: Retrofit? = null
//
//    fun getClient(context: Context): Retrofit {
//        if (retrofit == null) {
//            retrofit = Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(getOkHttpClient(context))
//                .build()
//        }
//        return retrofit!!
//    }
//
//    private fun getOkHttpClient(context: Context): OkHttpClient {
//        val logging = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//            return OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .addInterceptor { chain ->
//                    val token = getTokenFromStorage(context)
//                    val request = chain.request().newBuilder()
//                        .addHeader("Authorization", "Bearer $token")
//                        .build()
//
//                    // Log the URL and headers
//                    Log.d("OkHttp", "Request URL: ${request.url}")
//                    Log.d("OkHttp", "Request Headers: ${request.headers}")
//                // If the request has a body, log the body as well
//                    if (request.body != null) {
//                        Log.d("OkHttp", "Request Body: ${request.body}")
//                    }
//
//                    chain.proceed(request)
//                }
//                .build()
//        }
//    }
//private fun getTokenFromStorage(context: Context): String {
//    val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
//    val token = sharedPreferences.getString("auth_token", "") ?: ""
//    if (token.isEmpty()) {
//        Log.e("TokenError", "Authorization token is missing")
//    }
//    return token
//}


import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://noknok-staging-api.noknokgroceries.com/"

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build()
        }
        return retrofit!!
    }

    private fun getOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val bearerToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhcHBsaWNhdGlvbiIsInVpZCI6IkNzcDZUTDk0Ui02cHFmbnZvQVk4RXciLCJ1c2VySWQiOiI2NTU3MGZiMDljZWYyYjZlOTVhNzRiNzgiLCJyb2xlcyI6WyJVU0VSIl0sImFwaVR5cGUiOiJBUFAiLCJ0YWdzIjpbIk1BUktFVF9BUFBfVVNFUiJdLCJpYXQiOjE3MDUzMjQ4NjksInRlbmFudCI6IlhMbjJXSXhkV3gwREJ0IiwidmVyc2lvbiI6Nn0.iUG0lSxkm1I6uU9uzBzKDHE9kJ63_vVYjaXQJvqylghwBd_SSBPhMNiHEGL27_2Y"

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $bearerToken")
                    .addHeader("Content-Type", "application/json")

                val jsonBody = """
                    {
                        "locality": {
                            "coordinates": {
                                "lat": 33.8868890356106,
                                "lon": 35.5191703140736
                            }
                        },
                        "isLazy": false,
                        "locale": "",
                        "appIdentifier": "f368bb3bf8323985",
                        "appName": "noknok",
                        "country": "Pakistan",
                        "operationUid": "eee"
                    }
                """.trimIndent()

                val body = jsonBody.toRequestBody("application/json".toMediaTypeOrNull())
                val request = requestBuilder.post(body).build()

                // Log the URL, headers, and body
                Log.d("OkHttp", "Request URL: ${request.url}")
                Log.d("OkHttp", "Request Headers: ${request.headers}")
                Log.d("OkHttp", "Request Body: $jsonBody")

                chain.proceed(request)
            }
            .build()
    }
}


