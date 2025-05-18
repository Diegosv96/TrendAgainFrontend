package com.example.trendagainfrontend.data.data.api

import com.example.trendagainfrontend.data.data.model.AuthResponse
import com.example.trendagainfrontend.data.data.model.Product
import com.example.trendagainfrontend.data.data.model.UserLoginRequest
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Obtener todas las prendas
    @GET("/products/getAll")
    suspend fun getProducts(): Response<List<Product>>

    // Subir una nueva prenda (POST)
    @POST("products/add")
    suspend fun uploadProduct(@Body prenda: Product): Response<Product>

    @POST("/auth/login")
    suspend fun loginUser(@Body loginRequest: UserLoginRequest): Response<AuthResponse>
}