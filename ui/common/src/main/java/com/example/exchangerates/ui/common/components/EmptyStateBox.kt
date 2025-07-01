package com.example.exchangerates.ui.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.exchangerates.ui.common.R
import com.example.exchangerates.ui.common.theme.AppTheme

@Composable
fun EmptyStateBox(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.favorites_list_empty),
            fontSize = 16.sp,
            color = AppTheme.color.mainColors.textSecondary,
            textAlign = TextAlign.Center
        )
    }

}
