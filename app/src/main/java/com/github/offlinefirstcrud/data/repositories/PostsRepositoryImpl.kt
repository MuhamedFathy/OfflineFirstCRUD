package com.github.offlinefirstcrud.data.repositories

import android.content.Context
import com.github.offlinefirstcrud.core.extension.isNetworkAvailable
import com.github.offlinefirstcrud.data.datasource.local.PostsLocalDS
import com.github.offlinefirstcrud.data.datasource.remote.PostsRemoteDS
import com.github.offlinefirstcrud.data.mapper.toDBEntity
import com.github.offlinefirstcrud.data.mapper.toResponse
import com.github.offlinefirstcrud.domain.exception.NoLocalDataException
import com.github.offlinefirstcrud.domain.model.PostEntity
import com.github.offlinefirstcrud.domain.repositories.PostsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postsRemoteDS: PostsRemoteDS,
    private val postsLocalDS: PostsLocalDS
) : PostsRepository {

    override suspend fun getPosts(): Flow<List<PostEntity>> {
        if (context.isNetworkAvailable()) {
            postsRemoteDS.getPosts()
                .collect { posts -> postsLocalDS.addPosts(posts.map { it.toDBEntity() }) }
        }

        return postsLocalDS.getPosts()
            .map { dbPosts ->
                if (dbPosts.isEmpty()) throw NoLocalDataException() else dbPosts.map { it.toDBEntity() }
            }
    }

    override suspend fun addPost(post: PostEntity): Flow<List<PostEntity>> {
        if (context.isNetworkAvailable()) {
            postsRemoteDS.addPost(post.toResponse())
                .collect { postsLocalDS.addPost(it.toDBEntity()) }
        } else {
            postsLocalDS.addPost(post.toDBEntity())
        }

        return postsLocalDS.getPosts()
            .map { dbPosts ->
                if (dbPosts.isEmpty()) throw NoLocalDataException() else dbPosts.map { it.toDBEntity() }
            }
    }

    override suspend fun updatePost(post: PostEntity): Flow<List<PostEntity>> {
        if (context.isNetworkAvailable()) {
            postsRemoteDS.updatePost(post.id, post.toResponse())
                .catch { emitAll(flow { postsLocalDS.updatePost(post.toDBEntity()) }) }
                .collect { postsLocalDS.updatePost(post.toDBEntity()) }
        } else {
            postsLocalDS.updatePost(post.toDBEntity())
        }

        return postsLocalDS.getPosts()
            .map { dbPosts ->
                if (dbPosts.isEmpty()) throw NoLocalDataException() else dbPosts.map { it.toDBEntity() }
            }
    }

    override suspend fun deletePost(postId: Int): Flow<List<PostEntity>> {
        if (context.isNetworkAvailable()) {
            postsRemoteDS.deletePost(postId)
                .collect { postsLocalDS.deletePost(postId) }
        } else {
            postsLocalDS.deletePost(postId)
        }

        return postsLocalDS.getPosts()
            .map { dbPosts ->
                if (dbPosts.isEmpty()) throw NoLocalDataException() else dbPosts.map { it.toDBEntity() }
            }
    }
}
