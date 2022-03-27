package id.fauwiiz.gitconnect

import android.app.Application
import id.fauwiiz.gitconnect.di.apiModule
import id.fauwiiz.gitconnect.di.repositoryModule
import id.fauwiiz.gitconnect.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    apiModule,
                    repositoryModule,
                    viewModelModule
                )

            )
        }
    }
}