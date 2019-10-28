package com.fahim.example_employee_app.dagger

import com.fahim.example_employee_app.retrofit.DummyDataService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule(private val base_url:String) {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesDummyDataService(retrofit: Retrofit):DummyDataService {
        return retrofit.create(DummyDataService::class.java)
    }
}