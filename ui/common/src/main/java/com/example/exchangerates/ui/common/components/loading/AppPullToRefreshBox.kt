package com.example.exchangerates.ui.common.components.loading

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppPullToRefreshBox(
    modifier: Modifier = Modifier,
    refreshState: PullToRefreshState = rememberPullToRefreshState(),
    isLoading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) = PullToRefreshBox(
    modifier = modifier,
    state = refreshState,
    isRefreshing = isLoading,
    onRefresh = onRefresh,
    indicator = {
        AppLoadingIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            isLoading = isLoading,
            refreshState = refreshState,
        )
    },
    content = content,
)