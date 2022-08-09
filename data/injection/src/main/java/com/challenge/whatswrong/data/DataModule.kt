package com.challenge.whatswrong.data

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.challenge.whatswrong.data.login.LoginRepositoryImpl
import com.challenge.whatswrong.data.repository.LoginRepository
import com.challenge.whatswrong.data.utils.checkBackgroundThread
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {
    @Binds
    abstract fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository

    companion object {
        @Provides
        fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
            checkBackgroundThread()
            return PreferenceManager.getDefaultSharedPreferences(context)
        }
    }
}
