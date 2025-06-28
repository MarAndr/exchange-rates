package com.example.exchangerates.ui.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

private const val BACK_RESULT_KEY = "BACK_RESULT"

private sealed interface BackResult<out R> {
    data object None : BackResult<Nothing>

    @JvmInline
    value class Value<R>(val value: R) : BackResult<R>
}

fun NavController.navigateUp(result: Any?) {
    previousBackStackEntry?.savedStateHandle?.set(key = BACK_RESULT_KEY, value = result)
    navigateUp()
}

private fun <R> NavBackStackEntry.consumeBackResult() {
    savedStateHandle.remove<R>(BACK_RESULT_KEY)
}

@Suppress("UNCHECKED_CAST")
private fun <R> NavBackStackEntry.peekBackResult(): BackResult<R> {
    return if (savedStateHandle.contains(BACK_RESULT_KEY)) {
        savedStateHandle.get<R>(BACK_RESULT_KEY).let { BackResult.Value(it as R) }
    } else {
        BackResult.None
    }
}

@Composable
fun <R> rememberBackResultHandler(navBackStackEntry: NavBackStackEntry): BackResultHandler<R> {
    return remember(navBackStackEntry) {
        BackResultHandlerImpl(navBackStackEntry)
    }
}

interface BackResultHandler<R> {
    fun handleResultIfPresent(block: (R) -> Unit)
    fun getLifecycle(): Lifecycle?
}

private class BackResultHandlerImpl<R>(
    private val navBackStackEntry: NavBackStackEntry,
) : BackResultHandler<R> {
    override fun getLifecycle() = navBackStackEntry.lifecycle

    override fun handleResultIfPresent(block: (R) -> Unit) {
        when (val result = navBackStackEntry.peekBackResult<R>()) {
            BackResult.None -> Unit
            is BackResult.Value<R> -> {
                navBackStackEntry.consumeBackResult<R>()
                block(result.value)
            }
        }
    }
}

class PreviewBackResultHandler<R> : BackResultHandler<R> {
    override fun getLifecycle() = null
    override fun handleResultIfPresent(block: (R) -> Unit) = Unit
}

@Composable
fun <R> BackResultEffect(
    backResultHandler: BackResultHandler<R>,
    effect: (R) -> Unit,
) {
    val currentEffect by rememberUpdatedState(effect)
    DisposableEffect(backResultHandler) {
        val entryLifecycle = backResultHandler.getLifecycle()
        val observer = object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_START,
                    Lifecycle.Event.ON_RESUME,
                        -> {
                        backResultHandler.handleResultIfPresent(currentEffect)
                    }

                    Lifecycle.Event.ON_DESTROY -> {
                        entryLifecycle?.removeObserver(this)
                    }

                    else -> Unit
                }
            }
        }

        entryLifecycle?.addObserver(observer)

        onDispose {
            entryLifecycle?.removeObserver(observer)
        }
    }
}