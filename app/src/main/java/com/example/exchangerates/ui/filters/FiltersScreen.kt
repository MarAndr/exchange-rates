package com.example.exchangerates.ui.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.exchangerates.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exchangerates.ui.common.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersScreen(
    currentSortOption: SortOption,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onApplyClick: (SortOption) -> Unit,
) {
    var selectedOption by remember { mutableStateOf(currentSortOption) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        color = AppTheme.color.mainColors.textDefault,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        text = stringResource(R.string.filters_title),
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.color.bg.header,
                    titleContentColor = AppTheme.color.mainColors.textDefault,
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            tint = AppTheme.color.mainColors.primary,
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        containerColor = AppTheme.color.bg.default,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            SortOptions(
                selectedOption = selectedOption,
                onOptionSelected = { option ->
                    selectedOption = option
                },
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonColors(
                    containerColor = AppTheme.color.mainColors.primary,
                    contentColor = AppTheme.color.mainColors.onPrimary,
                    disabledContainerColor = AppTheme.color.mainColors.textDefault,
                    disabledContentColor = AppTheme.color.mainColors.secondary
                ), 
                onClick = {
                    onApplyClick(selectedOption)
                }
            ) {
                Text(stringResource(R.string.apply))
            }
        }
    }
}

@Composable
@Preview(showBackground = true, apiLevel = 34)
private fun FiltersScreenPreview() = AppTheme {
    FiltersScreen(
        currentSortOption = SortOption.CodeAZ,
        onBackClick = {},
        onApplyClick = {},
    )
} 