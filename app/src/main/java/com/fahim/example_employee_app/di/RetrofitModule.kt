package com.fahim.example_employee_app.di

import com.fahim.example_employee_app.api.DummyDataService
import dagger.Module
import dagger.Provides
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    @Named("base_url")
    fun provideUrl() = "http://dummy.restapiexample.com/api/v1/"

    @Provides
    @Singleton
    fun provideConverterFactory() : Converter.Factory = GsonConverterFactory.create()


    @Provides
    @Singleton
    fun provideRetrofit(@Named("base_url") url : String, converter: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(converter)
            .build()
    }

    @Provides
    @Singleton
    fun providesDummyDataService(retrofit: Retrofit):DummyDataService = retrofit.create(DummyDataService::class.java)
}