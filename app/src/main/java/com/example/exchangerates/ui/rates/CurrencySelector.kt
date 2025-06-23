package com.example.exchangerates.ui.rates

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exchangerates.R
import com.example.exchangerates.features.rates.api.model.Currency
import com.example.exchangerates.ui.common.theme.AppTheme

@Composable
fun CurrencySelector(
    baseCurrency: String,
    availableCurrencies: List<Currency>,
    onBaseCurrencyChanged: (String) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .border(
                        width = 1.dp,
                        color = AppTheme.color.mainColors.secondary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = baseCurrency,
                        modifier = Modifier.weight(1f),
                        color = AppTheme.color.mainColors.textDefault,
                    )
                    Icon(
                        painter = painterResource(id = if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down),
                        contentDescription = "Dropdown",
                        tint = AppTheme.color.mainColors.primary,
                    )
                }
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.8f) // This might need adjustment based on parent
        ) {
            availableCurrencies.forEach { currency ->
                DropdownMenuItem(
                    text = { Text(currency.symbol) },
                    onClick = {
                        onBaseCurrencyChanged(currency.symbol)
                        expanded = false
                    }
                )
            }
        }
        Spacer(Modifier.width(8.dp))
        IconButton(
            modifier = Modifier.border(
                width = 1.dp,
                color = AppTheme.color.mainColors.secondary,
                shape = RoundedCornerShape(8.dp)
            ),
            onClick = onFilterClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "Filter",
                tint = AppTheme.color.mainColors.primary,
            )
        }
    }
}

@Preview(
    name = "CurrencySelector - Default",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    apiLevel = 34,
)
@Composable
fun CurrencySelectorPreview() {
    AppTheme {
        CurrencySelector(
            baseCurrency = "USD",
            availableCurrencies = listOf(
                com.example.exchangerates.features.rates.api.model.Currency("US Dollar", "USD"),
                com.example.exchangerates.features.rates.api.model.Currency("Euro", "EUR")
            ),
            onBaseCurrencyChanged = {},
            onFilterClick = {}
        )
    }
}