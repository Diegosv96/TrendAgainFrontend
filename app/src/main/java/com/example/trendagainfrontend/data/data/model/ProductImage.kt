package com.example.trendagainfrontend.data.data.model

import java.util.Date

data class ProductImage(
    val id: Int,
    val product: Product?, // Nullable para evitar referencias circulares si no se necesita
    val imageUrl: String,
    val createdAt: Date?
)