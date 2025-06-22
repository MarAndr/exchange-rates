package com.example.exchangerates.ui.filters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exchangerates.R
import com.example.exchangerates.ui.common.theme.AppTheme

@Composable
fun SortOptions(
    modifier: Modifier = Modifier,
    selectedOption: SortOption,
    onOptionSelected: (SortOption) -> Unit
) {
    val appColors = AppTheme.color
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.sort_by),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = appColors.mainColors.textSecondary,
            ),
            fontSize = 12.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        SortOption.entries.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onOptionSelected(option) },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(option.labelResId),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    color = appColors.mainColors.textDefault,
                )
                RadioButton(
                    selected = selectedOption == option,
                    onClick = { onOptionSelected(option) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = appColors.mainColors.primary,
                        unselectedColor = appColors.mainColors.secondary,
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun SortOptionsPreview() {
    AppTheme {
        var selectedOption by remember { mutableStateOf(SortOption.CodeAZ) }

        SortOptions(
            selectedOption = selectedOption,
            onOptionSelected = { selectedOption = it }
        )
    }
}

enum class SortOption(val labelResId: Int) {
    CodeAZ(R.string.sort_code_az),
    CodeZA(R.string.sort_code_za),
    QuoteAsc(R.string.sort_quote_asc),
    QuoteDesc(R.string.sort_quote_desc)
}

