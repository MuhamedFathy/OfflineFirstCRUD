package com.github.offlinefirstcrud.data.datasource.remote

import com.github.offlinefirstcrud.data.datasource.remote.models.PostsResponse
import kotlinx.coroutines.flow.Flow

interface PostsRemoteDS {

    suspend fun getPosts(): Flow<List<PostsResponse>>
    suspend fun addPost(post: PostsResponse): Flow<PostsResponse>
    suspend fun updatePost(id: Int, post: PostsResponse): Flow<PostsResponse>
    suspend fun deletePost(id: Int): Flow<Unit>
}