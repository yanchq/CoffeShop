package com.example.coffeshop.di

import android.content.Context
import androidx.room.Room
import com.example.coffeshop.data.AppDatabase
import com.example.coffeshop.data.OrderListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).build()
    }

    @Provides
    fun provideOrderListDao(database: AppDatabase): OrderListDao {
        return database.orderListDao()
    }
}