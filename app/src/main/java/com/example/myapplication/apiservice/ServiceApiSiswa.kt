package com.example.myapplication.apiservice

import com.example.myapplication.modeldata.DataSiswa
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

// Interface untuk mendefinisikan endpoint API
interface ServiceApiSiswa {

    @GET("bacateman.php")
    suspend fun getSiswa(): List<DataSiswa>

    @POST("insertTM.php")
    suspend fun postSiswa(@Body dataSiswa: DataSiswa): Response<Void>

    @GET("baca1teman.php/{id}")
    suspend fun getSatuSiswa(@Query("id") id: Int): DataSiswa

    @PUT("editTM.php/{id}")
    suspend fun editSatuSiswa(@Query("id") id: Int, @Body dataSiswa: DataSiswa): retrofit2.Response<Void>

    @DELETE("deleteTM.php/{id}")
    suspend fun hapusSatuSiswa(@Query("id") id: Int): retrofit2.Response<Void>
}