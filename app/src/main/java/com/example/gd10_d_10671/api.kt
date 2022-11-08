package com.example.gd10_d_10671

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface api {
    @GET("mahasiswa/{cari}")
    fun getData(@Path("cari") cari:String? = null):
            Call<ResponseDataMahasiswa>

    @FormUrlEncoded
    @POST("mahasiswa")
    fun createData(
        @Field("mhsnobp") mhsnobp:String?,
        @Field("mhsnama") mhsnama:String?,
        @Field("mhsalamat") mhsalamat:String?,
        @Field("prodinama") prodinama:String?,
        @Field("mhstgllhr") mhstgllhr:String?,
    ):Call<ResponseCreate>

    @DELETE("mahasiswa/{mhsnobp}")
    fun deleteData(@Path("mhsnobp")mhsnobp:
                   String?):Call<ResponseDataMahasiswa>

    @FormUrlEncoded
    @PUT("mahasiswa/{mhsnobp}")
    fun updateData(
        @Path("mhsnobp") mhsnobp:String?,
        @Field("mhsnama") mhsnama:String?,
        @Field("mhsalamat") mhsalamat:String?,
        @Field("prodinama") prodinama:String?,
        @Field("mhstgllhr") mhstgllhr:String?,
    ):Call<ResponseCreate>
}