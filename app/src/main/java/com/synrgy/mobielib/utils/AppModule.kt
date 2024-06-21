package com.synrgy.mobielib.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.synrgy.data.SessionPreferences
import com.synrgy.data.local.DatabaseImpl
import com.synrgy.data.local.MIGRATION_2_3
import com.synrgy.data.remote.api.ApiService
import com.synrgy.data.repository.MovieRepositoryImpl
import com.synrgy.data.repository.UserRepositoryImpl
import com.synrgy.domain.repository.MovieRepository
import com.synrgy.domain.repository.UserRepository
import com.synrgy.mobielib.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesApiService(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val authInterceptor = Interceptor { chain ->
            val origin = chain.request()
            val requestBuilder = origin.newBuilder()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", BuildConfig.AUTHORIZATION)
            val request = requestBuilder.build()
            chain.proceed(request)
        }

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Singleton
    @Provides
    fun provideSessionPreferences(dataStore: DataStore<Preferences>): SessionPreferences {
        return SessionPreferences(dataStore)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): DatabaseImpl {
        Log.d("AppModule", "provideDatabase: called")
        return Room.databaseBuilder(
            context.applicationContext,
            DatabaseImpl::class.java,
            "movie_database"
        )
            .addMigrations(MIGRATION_2_3)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieRepo(
        apiService: ApiService,
        databaseImpl: DatabaseImpl
    ): MovieRepository {
        return MovieRepositoryImpl(apiService,databaseImpl)
    }

    @Singleton
    @Provides
    fun providesUserRepo(
        preferences: SessionPreferences,
        databaseImpl: DatabaseImpl,
    ) : UserRepository {
        return UserRepositoryImpl(preferences, databaseImpl)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")
}