package com.example.task1.data.retrofit

import android.content.Context
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "http://your_api_base_url/"

    private var retrofit: Retrofit? = null

    fun getClient(context: Context): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .client(getOkHttpClient(context))
                .build()
        }
        return retrofit!!
    }

    private fun getOkHttpClient(context: Context): OkHttpClient {
        // Add your OkHttpClient configuration here, such as logging interceptor, timeouts, etc.
        return OkHttpClient.Builder()
            .build()
    }





    }



