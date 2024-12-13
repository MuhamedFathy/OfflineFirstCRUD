package com.github.offlinefirstcrud.domain.interactor

import com.github.offlinefirstcrud.domain.repositories.PostsRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val repository: PostsRepository
) {
    suspend fun build(postId: Int) = repository.deletePost(postId)
}
