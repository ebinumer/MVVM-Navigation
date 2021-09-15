package com.whyte.test

import androidx.multidex.MultiDexApplication
import com.whyte.test.di.mNetworkModule
import com.whyte.test.di.mSessionManager
import com.whyte.test.di.mViewmodel
import com.whyte.test.di.repo
import com.whyte.test.utils.SessionManager
import com.yariksoffice.lingver.Lingver
import com.yariksoffice.lingver.store.PreferenceLocaleStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import java.util.*

class App : MultiDexApplication() {



    override fun onCreate() {
        super.onCreate()
//        if (BuildConfig.DEBUG)
        Timber.plant(Timber.DebugTree())

        startKoin {
            printLogger()
            androidContext(this@App)
            modules(
                listOf(
                    mSessionManager,
                    mViewmodel,
                    repo, mNetworkModule
                )
            )
        }
        val mSessionManager= SessionManager(this)
        val store= PreferenceLocaleStore(this, Locale(LANGUAGE_ENGLISH))
        val lingver= Lingver.init(this,store)
    }


    companion object {
        const val LANGUAGE_ENGLISH = "en"
        const val LANGUAGE_ARABIC = "ar"
    }
}