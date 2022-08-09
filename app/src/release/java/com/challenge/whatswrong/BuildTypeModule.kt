package com.challenge.whatswrong

import android.os.StrictMode
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber

@InstallIn(SingletonComponent::class)
@Module
internal object BuildTypeModule {
    @Provides
    fun providesThreadPolicy(): StrictMode.ThreadPolicy {
        return StrictMode.ThreadPolicy.LAX
    }

    @Provides
    fun providesVmPolicy(): StrictMode.VmPolicy {
        return StrictMode.VmPolicy.LAX
    }

    @Provides
    fun providesTree(): Timber.Tree {
        return object : Timber.Tree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                // Do nothing
            }
        }
    }
}
