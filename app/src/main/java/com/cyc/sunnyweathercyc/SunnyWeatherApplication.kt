package com.cyc.sunnyweathercyc

import android.app.Application
import android.content.Context

class SunnyWeatherApplication :Application() {
    companion object{
        lateinit var context: Context//全局context
        const val TOKEN = "wCbXKtjrWeffH6Km"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}