package com.example.exchangerates.ui.common.components.loading

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.exchangerates.ui.common.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppLoadingIndicator(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    refreshState: PullToRefreshState,
) {
    Indicator(
        modifier = modifier,
        isRefreshing = isLoading,
        state = refreshState,
        containerColor = AppTheme.color.bg.default,
        color = AppTheme.color.mainColors.primary,
    )
}