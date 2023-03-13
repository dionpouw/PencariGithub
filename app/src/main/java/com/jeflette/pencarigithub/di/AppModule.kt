package com.jeflette.pencarigithub.di

import android.app.Application
import androidx.room.Room
import androidx.viewbinding.BuildConfig
import com.jeflette.pencarigithub.BuildConfig.API_KEY
import com.jeflette.pencarigithub.BuildConfig.BASE_URL
import com.jeflette.pencarigithub.data.GithubUserRepository
import com.jeflette.pencarigithub.data.local.LocalDataSource
import com.jeflette.pencarigithub.data.local.db.UserDatabase
import com.jeflette.pencarigithub.data.preference.UserPreferencesRepository
import com.jeflette.pencarigithub.data.remote.RemoteDataSource
import com.jeflette.pencarigithub.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun providesOkhttpInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val req = chain.request()
            val requestBuilder =
                req.newBuilder().addHeader("Authorization", ": token $API_KEY").build()
            chain.proceed(requestBuilder)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor, interceptor: Interceptor
    ): OkHttpClient = if (BuildConfig.DEBUG) {
        OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).client(okHttpClient).build()

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): UserDatabase = Room.databaseBuilder(
        app, UserDatabase::class.java, "user_database"
    ).build()

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService) = RemoteDataSource(apiService)

    @Provides
    @Singleton
    fun provideLocalDataSource(db: UserDatabase) = db.userDao()

    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        userPreferencesRepository: UserPreferencesRepository
    ) = GithubUserRepository(remoteDataSource, localDataSource, userPreferencesRepository)
}