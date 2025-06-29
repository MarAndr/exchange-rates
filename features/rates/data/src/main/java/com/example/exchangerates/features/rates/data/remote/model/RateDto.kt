package com.example.exchangerates.features.rates.data.remote.model

import java.util.Date

internal data class RateDto(
    val id: Long = 0,
    val success: Boolean,
    val timestamp: Long?,
    val base: String?,
    val date: Date?,
    val rates: Map<String, Double>,
)