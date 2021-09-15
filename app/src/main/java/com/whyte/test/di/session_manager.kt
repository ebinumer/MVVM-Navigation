package com.whyte.test.di

import com.whyte.test.utils.SessionManager
import org.koin.dsl.module

val mSessionManager = module {
    single {
        SessionManager(get())
    }
}