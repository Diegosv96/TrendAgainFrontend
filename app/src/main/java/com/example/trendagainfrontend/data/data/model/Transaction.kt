package com.example.trendagainfrontend.data.data.model

import java.util.Date

data class Transaction(
    val buyer: User,
    val product: Product,
    val amount: Double,
    val transactionDate: String?
)