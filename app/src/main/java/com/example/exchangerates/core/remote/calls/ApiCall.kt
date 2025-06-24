package com.example.exchangerates.core.remote.calls

import com.example.exchangerates.features.common.loading.LoadingState
import kotlinx.coroutines.flow.flow

fun <ApiModel, DomainModel> apiCall(
    mapper: (ApiModel) -> DomainModel,
    call: suspend () -> ApiModel,
) = flow {
    emit(LoadingState.Loading)
    val result = try {
        val apiModel = call()
        val mapped = mapper(apiModel)
        LoadingState.Success(mapped)
    } catch (e: Exception) {
        e.printStackTrace()
        LoadingState.Error
    }
    emit(result)
}