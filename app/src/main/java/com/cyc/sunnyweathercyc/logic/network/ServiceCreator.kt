package com.cyc.sunnyweathercyc.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit构建器
 * 使用 inline和reified 的泛型实化进行简化
 */
object ServiceCreator {
    private const val BASE_URL = " https://api.caiyunapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T>create(serviceClass: Class<T>):T = retrofit.create(serviceClass)

    inline fun <reified T> create() :T = create(T::class.java)
}