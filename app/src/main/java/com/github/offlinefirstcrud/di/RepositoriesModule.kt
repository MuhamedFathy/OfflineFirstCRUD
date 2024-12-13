package com.github.offlinefirstcrud.di

import com.github.offlinefirstcrud.data.repositories.PostsRepositoryImpl
import com.github.offlinefirstcrud.domain.repositories.PostsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindPostsRepository(postsRepositoryImpl: PostsRepositoryImpl): PostsRepository
}
