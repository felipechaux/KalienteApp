/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.network.di

import android.content.Context
import com.example.network.data.APIService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.example.network.utils.APIConstants.BASE_API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        const val TIME_OUT = 10L
    }

    @Provides
    fun provideNASAService(@Named("retrofit") retrofit: Retrofit): APIService =
        retrofit.create(APIService::class.java)

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    @Named("baseUrl")
    fun provideBaseUrl(): String = BASE_API_URL

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = run {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)

                    },
            )
            .build()
    }

    @Provides
    @Named("retrofit")
    fun provideRetrofit(
        @Named("baseUrl") baseUrl: String,
        @Named("okHttpClient") client: OkHttpClient,
        gson: Gson,
        okhttpCallFactory: dagger.Lazy<Call.Factory>,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .callFactory { okhttpCallFactory.get().newCall(it) }
        .build()

    @Provides
    @Named("okHttpClient")
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            interceptors().add(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                },
            )
            hostnameVerifier { _, _ -> true }
            readTimeout(TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        }
        return builder.build()
    }
}
