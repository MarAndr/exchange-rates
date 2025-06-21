package com.example.exchangerates.ui.rates

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SortOptions(
    selectedOption: SortOption,
    onOptionSelected: (SortOption) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "SORT BY",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
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
                    text = option.label,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                RadioButton(
                    selected = selectedOption == option,
                    onClick = { onOptionSelected(option) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFF0023A0), // насыщенный синий
                        unselectedColor = Color(0xFFB0BFE5) // светло-синий
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SortOptionsPreview() {
    var selectedOption by remember { mutableStateOf(SortOption.CodeAZ) }

    SortOptions(
        selectedOption = selectedOption,
        onOptionSelected = { selectedOption = it }
    )
}

enum class SortOption(val label: String) {
    CodeAZ("Code A–Z"),
    CodeZA("Code Z–A"),
    QuoteAsc("Quote Asc."),
    QuoteDesc("Quote Desc.")
}

