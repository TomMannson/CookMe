package com.tommannson.remote

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit


object RetrofitFactory {

    private var retrofit: Retrofit? = null

    private fun provideRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return retrofit ?: Retrofit.Builder()
            .baseUrl("https://hook.eu2.make.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()
            .also {
                retrofit = it
            }
    }

    fun providerService(): RecipeApi {
        return provideRetrofit().create(RecipeApi::class.java)
    }

}

interface RecipeApi {
    @FormUrlEncoded
    @POST("fzkhigo16ah1dkik6uwq37fkf8tr7abj")
    fun recognizeRecipe(
        @FieldMap rateApi: Map<String?, String?>?
    ): Call<String>
}