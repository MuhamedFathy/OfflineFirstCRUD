package com.github.offlinefirstcrud.data.datasource.remote

import com.github.offlinefirstcrud.data.datasource.remote.models.PostsResponse
import com.github.offlinefirstcrud.data.datasource.remote.network.PostsAPI
import com.github.offlinefirstcrud.data.extension.parseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostsRemoteDSImpl @Inject constructor(
    private val postsApi: PostsAPI
) : PostsRemoteDS {

    override suspend fun getPosts(): Flow<List<PostsResponse>> =
        flow { emit(postsApi.getPosts()) }
            .parseResponse()

    override suspend fun addPost(post: PostsResponse): Flow<PostsResponse> =
        flow { emit(postsApi.addPost(post)) }
            .parseResponse()

    override suspend fun updatePost(id: Int, post: PostsResponse): Flow<PostsResponse> =
        flow { emit(postsApi.updatePost(id, post)) }
            .parseResponse()

    override suspend fun deletePost(id: Int) =
        flow { emit(postsApi.deletePost(id)) }
            .parseResponse()
}
