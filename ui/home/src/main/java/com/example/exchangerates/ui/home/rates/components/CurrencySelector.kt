@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.exchangerates.ui.home.rates.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.exchangerates.ui.common.R
import com.example.exchangerates.features.rates.entities.Currency
import com.example.exchangerates.features.rates.entities.CurrencySymbol
import com.example.exchangerates.ui.common.theme.AppTheme

@Composable
internal fun CurrencySelector(
    baseCurrency: CurrencySymbol,
    availableCurrencies: List<Currency>,
    onBaseCurrencyChanged: (CurrencySymbol) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val density = LocalDensity.current
        var dropdownWidth by remember { mutableIntStateOf(0) }
        val dropdownWidthDp = remember(dropdownWidth) {
            with(density) {
                dropdownWidth.toDp()
            }
        }
        var dropdownHeight by remember { mutableIntStateOf(0) }
        val dropdownHeightDp = remember(dropdownHeight) {
            with(density) {
                dropdownHeight.toDp()
            }
        }
        Box(
            modifier = Modifier
                .onSizeChanged {
                    dropdownWidth = it.width
                    dropdownHeight = it.height
                }
                .weight(1f)
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(AppTheme.color.bg.default)
                    .clickable {
                        expanded = !expanded
                    }
                    .border(
                        width = 1.dp,
                        color = AppTheme.color.mainColors.secondary,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = baseCurrency.value,
                    modifier = Modifier.weight(1f),
                    color = AppTheme.color.mainColors.textDefault,
                )
                Icon(
                    painter = painterResource(id = if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down),
                    contentDescription = "Dropdown",
                    tint = AppTheme.color.mainColors.primary,
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = AppTheme.color.bg.default,
                border = BorderStroke(1.dp, AppTheme.color.mainColors.secondary),
                shape = RoundedCornerShape(8.dp),
                offset = DpOffset(0.dp, -dropdownHeightDp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .width(dropdownWidthDp)
                        .height(300.dp),
                ) {
                    items(availableCurrencies) { currency ->
                        DropdownMenuItem(
                            text = { Text(currency.symbol.value) },
                            colors = MenuDefaults.itemColors(
                                textColor = AppTheme.color.mainColors.textDefault,
                            ),
                            onClick = {
                                onBaseCurrencyChanged(currency.symbol)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        Spacer(Modifier.width(8.dp))
        IconButton(
            modifier = Modifier
                .background(
                    color = AppTheme.color.bg.default,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = AppTheme.color.mainColors.secondary,
                    shape = RoundedCornerShape(8.dp)
                ),
            onClick = onFilterClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = AppTheme.color.bg.default,
                contentColor = AppTheme.color.mainColors.primary,
            ),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "Filter",
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
            baseCurrency = CurrencySymbol("USD"),
            availableCurrencies = listOf(
                Currency("US Dollar", CurrencySymbol("USD")),
                Currency("Euro", CurrencySymbol("EUR"))
            ),
            onBaseCurrencyChanged = {},
            onFilterClick = {}
        )
    }
}