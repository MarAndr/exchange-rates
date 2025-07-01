package com.example.exchangerates.ui.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exchangerates.ui.common.R
import com.example.exchangerates.ui.common.theme.AppTheme

@Composable
fun ErrorBox(
    modifier: Modifier = Modifier,
    onRetryButtonClick: () -> Unit,
){
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.error_occurred_try_again),
                fontSize = 16.sp,
                color = AppTheme.color.mainColors.textSecondary,
                textAlign = TextAlign.Center
            )
            Button(
                colors = ButtonColors(
                    containerColor = AppTheme.color.mainColors.primary,
                    contentColor = AppTheme.color.mainColors.onPrimary,
                    disabledContainerColor = AppTheme.color.mainColors.textDefault,
                    disabledContentColor = AppTheme.color.mainColors.secondary
                ),
                onClick = onRetryButtonClick
            ) {
                Text(text = stringResource(R.string.refresh))
            }
        }
    }

}
