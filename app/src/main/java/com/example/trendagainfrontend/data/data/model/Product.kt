package com.example.trendagainfrontend.data.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Date

data class Product(
    val id: Int,
    val user: User,
    @SerializedName("product_images")
    val productImages: List<ProductImage> = emptyList(),
    val name: String,
    val description: String?,
    val price: Double,
    val category: Category?,
    @SerializedName("created_at")
    val createdAt: Date?,
    @SerializedName("updated_at")
    val updatedAt: Date?
): Serializable