package com.example.exchangerates.ui.main.navigation.di

import com.example.exchangerates.ui.common.navigation.AppNavigator
import com.example.exchangerates.ui.main.navigation.AppNavigatorImpl
import com.example.exchangerates.ui.main.navigation.NavigationEventsProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface NavigationModule {
    @Binds
    @Singleton
    fun bindAppNavigator(impl: AppNavigatorImpl): AppNavigator

    @Binds
    @Singleton
    fun bindNavigationEventsProvider(impl: AppNavigatorImpl): NavigationEventsProvider
}