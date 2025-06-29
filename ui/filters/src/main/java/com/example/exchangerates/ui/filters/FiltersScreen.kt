package com.example.exchangerates.ui.filters

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.exchangerates.ui.common.R
import com.example.exchangerates.features.filters.api.model.SortOption
import com.example.exchangerates.ui.filters.components.SortOptions
import com.example.exchangerates.ui.common.theme.AppTheme
import com.example.exchangerates.ui.filters.state.FiltersScreenEvent
import com.example.exchangerates.ui.filters.state.FiltersScreenState
import com.example.exchangerates.ui.filters.state.FiltersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersScreen(
    viewModel: FiltersViewModel = hiltViewModel(),
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    FiltersScreen(
        screenState = screenState,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersScreen(
    screenState: FiltersScreenState,
    onEvent: (FiltersScreenEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
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
                    IconButton(
                        onClick = { onEvent(FiltersScreenEvent.OnBackClicked) },
                    ) {
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
                selectedOption = screenState.sortOption,
                onOptionSelected = { option ->
                    onEvent(FiltersScreenEvent.SortOptionSelected(option))
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
                    onEvent(FiltersScreenEvent.OnApply(screenState.sortOption))
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
        screenState = FiltersScreenState(SortOption.QuoteAsc),
        onEvent = {},
    )
} 