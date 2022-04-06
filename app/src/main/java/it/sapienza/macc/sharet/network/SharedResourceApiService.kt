package it.sapienza.macc.sharet.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import it.sapienza.macc.sharet.domain.SharedResource
import it.sapienza.macc.sharet.domain.User
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


private const val BASE_URL = "http://10.0.2.2:8000/"


interface SharedResourceApiService {

    @GET("resource/all")
    fun getSharedResourcesAsync(): Deferred<SharedResourceDtoContainer>

    @GET("resource/{user_id}")
    fun getSharedResources(@Path("user_id") user_id: String): Deferred<SharedResourceDtoContainer>

    /*@Headers("Content-Type: application/json")*/
    @POST("resource/")
    fun addResource(@Body resourceData: SharedResource): Deferred<SharedResourceDto>

    /*@Headers("Content-Type: application/json")*/
    @POST("user/")
    fun addUser(@Body userData: User): Deferred<User>

    @DELETE("resource/{resource_id}")
    fun deleteResource(@Path("resource_id") resource_id: Int): Deferred<String>
}

/**
 * Build the Moshi object that Retrofit will be using,
 * making sure to add the Kotlin adapter for full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object SharedResourceApi {

    private val client = OkHttpClient.Builder().build()

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(client)
        .build()

    val retrofitService: SharedResourceApiService = retrofit.create(SharedResourceApiService::class.java)
}