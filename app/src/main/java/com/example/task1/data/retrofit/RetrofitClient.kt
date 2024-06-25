
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory






object RetrofitClient {
    private const val BASE_URL = "https://noknok-staging-api.noknokgroceries.com/"

    private var retrofit: Retrofit? = null

    fun getClient(headers: Map<String, String>): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient(headers))
                .build()
        }
        return retrofit!!
    }

    private fun getOkHttpClient(headers: Map<String, String>): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                headers.forEach { (key, value) ->
                    requestBuilder.addHeader(key, value)
                }

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }
}


