package com.example.idatdemo.data.apí

import com.example.idatdemo.entity.Producto
import retrofit2.Call
import retrofit2.http.GET

interface FakeStoreApiService {

    @GET("products")
    fun getProducts() : Call<List<Producto>>

}