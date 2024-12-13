package com.github.offlinefirstcrud.di

import com.github.offlinefirstcrud.data.datasource.remote.PostsRemoteDS
import com.github.offlinefirstcrud.data.datasource.remote.PostsRemoteDSImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DatasourceModule {

    @Binds
    abstract fun bindPostsRemoteDS(postsRemoteDS: PostsRemoteDSImpl): PostsRemoteDS
}
