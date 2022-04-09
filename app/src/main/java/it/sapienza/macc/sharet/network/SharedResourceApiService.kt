package it.sapienza.macc.sharet.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import it.sapienza.macc.sharet.domain.Reservation
import it.sapienza.macc.sharet.domain.SharedResource
import it.sapienza.macc.sharet.domain.User
import it.sapienza.macc.sharet.domain.UserAndResource
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


private const val BASE_URL = "https://sharet-2022.herokuapp.com/"
private const val LOCALHOST = "http://10.0.2.2:8000/"


interface SharedResourceApiService {

    @GET("resource/all")
    fun getSharedResourcesAsync(): Deferred<SharedResourceDtoContainer>


    @GET("iduser/{idUser}")
    fun getUserWithIdUserAsync(@Path("idUser") idUser: Int): Deferred<UserDto>

    @GET("user/name/{name}")
    fun getUserWithNameAsync(@Path("name") name: String): Deferred<UserDto>

    @GET("user/email/{email}")
    fun getUserWithEmailAsync(@Path("email") email: String): Deferred<UserDto>

    @GET("resource/{user_id}")
    fun getSharedResourcesAsync(@Path("user_id") user_id: String): Deferred<SharedResourceDtoContainer>

    @GET("resource/{user_id}")
    fun getUserAndResourcesAsync(@Path("user_id") user_id: String): Deferred<UserAndResourceDtoContainer>

    @GET("resource/get/{resource_id}")
    fun getResourceAsync(@Path("resource_id") resource_id: Int): Deferred<SharedResourceDto>

    @GET("reservation/{resource_id}/{date}")
    fun getReservationAsync(@Path("resource_id") resource_id: Int, @Path("date") date: String): Deferred<ReservationDtoContainer>

    @POST("resource/")
    fun addResourceAsync(@Body resourceData: SharedResource): Deferred<SharedResourceDto>

    @POST("user/")
    fun addUserAsync(@Body userData: User): Deferred<User>

    @POST("reservation/")
    fun addReservationAsync(@Body reservationData: Reservation): Deferred<ReservationDto>

    @DELETE("resource/{resource_id}")
    fun deleteResourceAsync(@Path("resource_id") resource_id: Int): Deferred<String>

    @DELETE("reservation/{reservation_id}")
    fun deleteReservationAsync(@Path("reservation_id") reservation_id: Int): Deferred<String>

    @POST("userandresource/")
    fun addUserAndResourceAsync(@Body userAndResource: UserAndResource): Deferred<UserAndResourceDto>




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