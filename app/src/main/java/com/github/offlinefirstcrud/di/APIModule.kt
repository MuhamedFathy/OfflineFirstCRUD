package com.github.offlinefirstcrud.di

import com.github.offlinefirstcrud.data.datasource.remote.network.PostsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class APIModule {

    @Singleton
    @Provides
    fun providePostsAPI(retrofit: Retrofit): PostsAPI = retrofit.create(PostsAPI::class.java)
}
