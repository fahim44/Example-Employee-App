package com.fahim.example_employee_app.di.appModule

import com.fahim.example_employee_app.api.DummyDataService
import com.fahim.example_employee_app.util.EmployeeKeys
import com.orhanobut.logger.Logger
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class RetrofitModule {


    @Provides
    @Singleton
    @Named(EmployeeKeys.BASE_URL)
    fun provideUrl() = "http://dummy.restapiexample.com/api/v1/"


    @Provides
    @Singleton
    fun provideConverterFactory() : Converter.Factory = GsonConverterFactory.create()


    @Provides
    @Singleton
    fun provideLoggingInterceptor() : HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Logger.log(Logger.INFO,"OkHttp",message,null)
            } })
        logging.level = HttpLoggingInterceptor.Level.BODY
        logging.redactHeader("Authorization")
        logging.redactHeader("Cookie")
        return logging
    }


    @Provides
    @Singleton
    fun provideOkHttp(interceptor: HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(@Named(EmployeeKeys.BASE_URL) url : String, converter: Converter.Factory, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(converter)
            .client(client)
            .build()
    }


    @Provides
    @Singleton
    fun providesDummyDataService(retrofit: Retrofit):DummyDataService = retrofit.create(DummyDataService::class.java)
}