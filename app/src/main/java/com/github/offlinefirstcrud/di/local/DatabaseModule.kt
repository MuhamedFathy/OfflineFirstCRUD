package com.github.offlinefirstcrud.di.local

import android.content.Context
import androidx.room.Room
import com.github.offlinefirstcrud.data.datasource.local.database.DATABASE_NAME
import com.github.offlinefirstcrud.data.datasource.local.database.PostsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun providePostsDatabase(@ApplicationContext context: Context): PostsDatabase {
        return Room
            .databaseBuilder(context, PostsDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideLocalPostsDao(database: PostsDatabase) = database.postsDao()
}
