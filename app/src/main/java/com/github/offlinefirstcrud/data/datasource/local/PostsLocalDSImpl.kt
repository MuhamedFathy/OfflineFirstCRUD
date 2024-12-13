package com.github.offlinefirstcrud.data.datasource.local

import com.github.offlinefirstcrud.data.datasource.local.database.PostsDao
import com.github.offlinefirstcrud.data.datasource.local.entity.PostDBEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostsLocalDSImpl @Inject constructor(
    private val dao: PostsDao
) : PostsLocalDS {

    override fun getPosts(): Flow<List<PostDBEntity>> = dao.getPosts()
    override suspend fun addPosts(post: List<PostDBEntity>) = dao.addPosts(post)
    override suspend fun addPost(post: PostDBEntity) = dao.addPost(post)
    override suspend fun updatePost(post: PostDBEntity) = dao.updatePost(post)
    override suspend fun deletePost(postId: Int) = dao.deletePost(postId)
}
