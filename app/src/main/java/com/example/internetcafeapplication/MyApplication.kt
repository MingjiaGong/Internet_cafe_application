package com.example.internetcafeapplication

import android.app.Application

import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

@HiltAndroidApp
class MyApplication:Application() {
//    override fun onCreate() {
//        super.onCreate()
//
//        GlobalContext.startKoin {
//            androidLogger()
//            androidContext(this@MyApplication)
//            modules(appModule)
//        }
//    }
}

//class MainApplication : Application(){
//    override fun onCreate() {
//        super.onCreate()
//
//        startKoin{
//            androidLogger()
//            androidContext(this@MainApplication)
//            modules(appModule)
//        }
//    }
//}

