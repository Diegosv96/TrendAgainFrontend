package com.example.trendagainfrontend.data.data.model

import java.util.Date

data class Rating(
    val id: Int,
    val user: User,
    val product: Product,
    val rating: Int,
    val review: String?,
    val createdAt: Date?
)