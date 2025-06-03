package com.example.trendagainfrontend.data.data.model

import java.util.Date

data class Favorite(
    val user: User,
    val product: Product,
    val createdAt: Date?
)