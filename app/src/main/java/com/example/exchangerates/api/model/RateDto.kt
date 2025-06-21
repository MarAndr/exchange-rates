package com.example.exchangerates.api.model

import com.example.exchangerates.domain.model.Symbol
import java.util.*

data class RateDto(
    val id: Long = 0,
    val success: Boolean,
    val timestamp: Long?,
    val base: String?,
    val date: Date?,
    val rates: Map<String, Double>?,
)