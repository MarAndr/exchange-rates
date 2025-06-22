package com.example.exchangerates.ui.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exchangerates.ui.common.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onOptionsSelected: (SortOption) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Filters",
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        SortOptions(
            modifier = Modifier.padding(paddingValues),
            selectedOption = SortOption.CodeAZ,
            onOptionSelected = onOptionsSelected,
        )
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 34)
private fun FiltersScreenPreview() = AppTheme {
    FiltersScreen(
        onBackClick = {},
        onOptionsSelected = {},
    )
} 