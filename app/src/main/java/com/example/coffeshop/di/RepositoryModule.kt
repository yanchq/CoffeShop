package com.example.coffeshop.di

import com.example.coffeshop.data.CoffeeShopRepositoryImpl
import com.example.coffeshop.domain.repository.CoffeeShopRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(impl: CoffeeShopRepositoryImpl): CoffeeShopRepository
}