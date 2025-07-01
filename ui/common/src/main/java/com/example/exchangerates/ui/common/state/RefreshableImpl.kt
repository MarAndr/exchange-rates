package com.example.exchangerates.ui.common.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerates.core.loading.LoadingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


fun <T> ViewModel.refreshable(
    createFlow: () -> Flow<LoadingState<T>>,
): Refreshable<T> =
    RefreshableImpl(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = RefreshLoadingState.Initial.Loading,
        createFlow = { createFlow() },
        paramFlow = flowOf(Unit),
    )

fun <T, P> ViewModel.refreshable(
    paramFlow: Flow<P>,
    createFlow: (p: P) -> Flow<LoadingState<T>>,
): Refreshable<T> =
    RefreshableImpl(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = RefreshLoadingState.Initial.Loading,
        createFlow = createFlow,
        paramFlow = paramFlow,
    )

private class RefreshableImpl<P, T>(
    private val scope: CoroutineScope,
    started: SharingStarted,
    initialValue: RefreshLoadingState<T>,
    paramFlow: Flow<P>,
    createFlow: (p: P) -> Flow<LoadingState<T>>,
) : Refreshable<T> {

    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1).apply {
        // This emits, but has no effect until someone actually starts collecting
        tryEmit(Unit)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val flow = paramFlow
        .flatMapLatest { param ->
            refreshTrigger
                .flatMapLatest { createFlow(param) }
                .aggregateEventsToRefreshLoadingState()
        }
        .stateIn(
            scope = scope,
            started = started,
            initialValue = initialValue,
        )

    override fun refresh() {
        scope.launch {
            refreshTrigger.emit(Unit)
        }
    }
}

fun <T> Flow<LoadingState<T>>.aggregateEventsToRefreshLoadingState(): Flow<RefreshLoadingState<T>> =
    runningFold<LoadingState<T>, RefreshLoadingState<T>>(
        initial = RefreshLoadingState.Initial.Loading,
    ) { accumulator, value ->
        accumulator.update(value)
    }.drop(1) // Drop the initial empty value

private fun <T> RefreshLoadingState<T>.update(loadingEvent: LoadingState<T>): RefreshLoadingState<T> {
    return when (this) {
        is RefreshLoadingState.Initial -> when (loadingEvent) {
            is LoadingState.Error -> RefreshLoadingState.Initial.Error
            is LoadingState.Loading -> RefreshLoadingState.Initial.Loading
            is LoadingState.Success -> RefreshLoadingState.Data(
                data = loadingEvent.data,
                isLoading = false,
                isError = false,
            )
        }

        is RefreshLoadingState.Data -> copy(
            data = if (loadingEvent is LoadingState.Success) loadingEvent.data else data,
            isError = loadingEvent is LoadingState.Error,
            isLoading = loadingEvent is LoadingState.Loading,
        )
    }
}