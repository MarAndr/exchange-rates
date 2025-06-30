package com.example.exchangerates.ui.home.rates

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exchangerates.ui.common.R
import com.example.exchangerates.ui.common.theme.AppTheme
import java.util.Locale

@Composable
fun RatesCard(
    modifier: Modifier = Modifier,
    title: String,
    rate: Double?,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    val appColors = AppTheme.color

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = appColors.bg.card, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = appColors.mainColors.textDefault,
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = rate?.let { String.format(Locale.getDefault(), "%.6f", rate) } ?: "-",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = appColors.mainColors.textDefault,
            )

            Spacer(Modifier.width(16.dp))

            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = onFavoriteClick,
                        indication = ripple(
                            bounded = false,
                        ),
                    ),
                painter = painterResource(
                    id = if (isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorites_off
                ),
                contentDescription = "Favorite",
                tint = Color.Unspecified
            )
        }
    }
}

@Preview(name = "USD - Favorite", showBackground = true, apiLevel = 34)
@Composable
private fun CurrencyCardUsdFavoritePreview() {
    AppTheme {
        RatesCard(
            title = "USD",
            rate = 1.234567,
            isFavorite = true,
            onFavoriteClick = {}
        )
    }
}