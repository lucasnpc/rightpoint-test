package com.challenge.whatswrong.remote

import com.challenge.whatswrong.remote.services.LoginService
import com.squareup.moshi.Moshi
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [NetworkSettings::class])
object NetworkModule {
    @Provides
    fun providesBaseUrl(): HttpUrl? {
        return "https://funtion-app-android-interview-challenge.azurewebsites.net/".toHttpUrlOrNull()
    }

    @Provides
    fun providesOkHttpClient(level: HttpLoggingInterceptor.Level): OkHttpClient {
        val logger = HttpLoggingInterceptor.Logger { message ->
            Timber.tag("OkHttp").v(message)
        }

        val logging = HttpLoggingInterceptor(logger).apply { this.level = level }

        return OkHttpClient.Builder()
//            .callTimeout(10L, TimeUnit.MILLISECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(url: HttpUrl?, client: Lazy<OkHttpClient>): Retrofit {
        val callFactory = Call.Factory { request -> client.get().newCall(request) }

        val moshi = Moshi.Builder().build()

        return Retrofit.Builder()
            .client(client.get())
            .baseUrl(requireNotNull(url))
            .callFactory(callFactory)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    fun providesLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }
}
