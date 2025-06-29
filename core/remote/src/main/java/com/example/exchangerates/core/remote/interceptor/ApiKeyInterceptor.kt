package com.example.exchangerates.core.remote.interceptor

import com.example.exchangerates.core.remote.Constants
import okhttp3.Interceptor
import okhttp3.Response

internal class ApiKeyInterceptor : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("apikey", Constants.API_KEY)
            .build()
        
        return chain.proceed(newRequest)
    }
} 