package com.app.geokaab.di

import com.app.geokaab.data.repository.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTypeExperienceRepository(
        database: FirebaseFirestore,
        storageReference: StorageReference
    ): TypeExperienceRepository{
        return TypeExperienceRepositoryImp(database,storageReference)
    }

    @Provides
    @Singleton
    fun provideExperienceRepository(
        database: FirebaseFirestore,
        storageReference: StorageReference
    ): ExperienceRepository {
        return ExperienceRepositoryImp(database,storageReference)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(
        database: FirebaseFirestore,
        storageReference: StorageReference
    ): LocationRepository {
        return LocationRepositoryImp(database,storageReference)
    }

    @Provides
    @Singleton
    fun provideContactRepository(
        database: FirebaseFirestore,
        storageReference: StorageReference
    ): ContactRepository {
        return ContacRepositoryImp(database,storageReference)
    }

}