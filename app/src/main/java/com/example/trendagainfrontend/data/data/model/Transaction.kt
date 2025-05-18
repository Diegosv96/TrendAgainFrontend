package com.example.trendagainfrontend.data.data.model

import java.util.Date

data class Transaction(
    val id: Int,
    val buyer: User,
    val product: Product,
    val amount: Double,
    val transactionDate: Date?
)