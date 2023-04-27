package com.app.geokaab.di

import com.app.geokaab.data.repository.ExperienceRepository
import com.app.geokaab.data.repository.ExperienceRepositoryImp
import com.app.geokaab.data.repository.TypeExperienceRepository
import com.app.geokaab.data.repository.TypeExperienceRepositoryImp
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

}