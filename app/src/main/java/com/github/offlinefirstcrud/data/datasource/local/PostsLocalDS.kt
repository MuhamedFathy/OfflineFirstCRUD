package com.github.offlinefirstcrud.data.datasource.local

import com.github.offlinefirstcrud.data.datasource.local.entity.PostDBEntity
import kotlinx.coroutines.flow.Flow

interface PostsLocalDS {

    fun getPosts(): Flow<List<PostDBEntity>>
    suspend fun addPost(post: PostDBEntity)
    suspend fun addPosts(post: List<PostDBEntity>)
    suspend fun updatePost(post: PostDBEntity)
    suspend fun deletePost(postId: Int)
}
