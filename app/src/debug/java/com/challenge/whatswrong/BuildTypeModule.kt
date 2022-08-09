package com.challenge.whatswrong

import android.os.Build
import android.os.StrictMode
import android.os.strictmode.DiskReadViolation
import android.os.strictmode.UntaggedSocketViolation
import android.os.strictmode.Violation
import androidx.annotation.RequiresApi
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
internal object BuildTypeModule {
    @Provides
    @Named("StrictMode")
    fun providesStrictModeExecutor(): ExecutorService = Executors.newSingleThreadExecutor()

    @Provides
    fun providesThreadPolicy(
        @Named("StrictMode") strictModeExecutor: Lazy<ExecutorService>
    ): StrictMode.ThreadPolicy {
        return StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .apply {
                if (Build.VERSION.SDK_INT >= 28) {
                    penaltyListener(strictModeExecutor.get()) { violation ->
                        Timber.tag("StrictMode").w(violation)
                    }
                } else {
                    penaltyLog()
                }
            }
            .build()
    }

    @Provides
    fun providesVmPolicy(
        @Named("StrictMode") strictModeExecutor: Lazy<ExecutorService>
    ): StrictMode.VmPolicy {
        return StrictMode.VmPolicy.Builder()
            .detectAll()
            .apply {
                if (Build.VERSION.SDK_INT >= 28) {
                    penaltyListener(strictModeExecutor.get()) { violation ->
                        if (violation.isIgnorable()) {
                            return@penaltyListener
                        }
                        Timber.tag("StrictMode").w(violation)
                    }
                } else {
                    penaltyLog()
                }
            }
            .build()
    }

    @Provides
    fun providesTree(): Timber.Tree {
        return Timber.DebugTree()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun Violation.isIgnorable(): Boolean {
        // Firebase and OkHttp don't tag sockets
        val untaggedSocket = this is UntaggedSocketViolation

        // PreferenceFragment hits preferences directly
        val preferenceFragment = (
            this is DiskReadViolation && this.stackTrace.any {
                it.methodName == "onCreatePreferences"
            }
            )

        return (untaggedSocket && preferenceFragment)
    }
}
