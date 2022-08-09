package com.challenge.whatswrong.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor.Level

@InstallIn(SingletonComponent::class)
@Module
internal object NetworkSettings {
    @Provides
    fun providesLoggingLevel(): Level = Level.NONE
}
