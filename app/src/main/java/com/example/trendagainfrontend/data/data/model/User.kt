package com.example.trendagainfrontend.data.data.model

import java.util.Date

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val passwordHash: String,
    val firstName: String?,
    val lastName: String?,
    val dateOfBirth: Date?,
    val createdAt: Date?,
    val updatedAt: Date?
)