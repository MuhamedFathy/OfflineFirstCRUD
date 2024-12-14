package com.github.offlinefirstcrud.domain.repositories

import com.github.offlinefirstcrud.domain.model.PostEntity
import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    suspend fun getPosts(): Flow<List<PostEntity>>
    suspend fun addPost(post: PostEntity): Flow<List<PostEntity>>
    suspend fun updatePost(post: PostEntity): Flow<List<PostEntity>>
    suspend fun deletePost(postId: Int): Flow<List<PostEntity>>
}
