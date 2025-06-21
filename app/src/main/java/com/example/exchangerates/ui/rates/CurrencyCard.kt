package com.example.exchangerates.ui.rates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exchangerates.ui.common.theme.AppTheme

@Composable
fun CurrencyCard(
    currencyCode: String,
    rate: Double,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    val appColors = AppTheme.color

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = appColors.bg.card, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = currencyCode,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF222222)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = String.format("%.6f", rate),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF222222)
            )

            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color(0xFFFFC107) else Color(0xFF90A4AE)
                )
            }
        }
    }
}

@Preview(name = "USD - Favorite", showBackground = true, apiLevel = 34)
@Composable
private fun CurrencyCardUsdFavoritePreview() {
    AppTheme {
        CurrencyCard(
            currencyCode = "USD",
            rate = 1.234567,
            isFavorite = true,
            onFavoriteClick = {}
        )
    }
}

@Preview(name = "EUR - Not Favorite", showBackground = true, apiLevel = 34)
@Composable
private fun CurrencyCardEurNotFavoritePreview() {
    AppTheme {
        CurrencyCard(
            currencyCode = "EUR",
            rate = 0.987654,
            isFavorite = false,
            onFavoriteClick = {}
        )
    }
}

@Preview(name = "GBP - High Rate", showBackground = true, apiLevel = 34)
@Composable
private fun CurrencyCardGbpHighRatePreview() {
    AppTheme {
        CurrencyCard(
            currencyCode = "GBP",
            rate = 2.123456,
            isFavorite = true,
            onFavoriteClick = {}
        )
    }
}

@Preview(name = "JPY - Low Rate", showBackground = true, apiLevel = 34)
@Composable
private fun CurrencyCardJpyLowRatePreview() {
    AppTheme {
        CurrencyCard(
            currencyCode = "JPY",
            rate = 0.001234,
            isFavorite = false,
            onFavoriteClick = {}
        )
    }
}

@Preview(name = "Multiple Currency Cards", showBackground = true, apiLevel = 34)
@Composable
private fun MultipleCurrencyCardsPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CurrencyCard(
                currencyCode = "USD",
                rate = 1.234567,
                isFavorite = true,
                onFavoriteClick = {}
            )
            CurrencyCard(
                currencyCode = "EUR",
                rate = 0.987654,
                isFavorite = false,
                onFavoriteClick = {}
            )
            CurrencyCard(
                currencyCode = "GBP",
                rate = 2.123456,
                isFavorite = true,
                onFavoriteClick = {}
            )
            CurrencyCard(
                currencyCode = "JPY",
                rate = 0.001234,
                isFavorite = false,
                onFavoriteClick = {}
            )
        }
    }
}
