package com.github.offlinefirstcrud.domain.interactor

import com.github.offlinefirstcrud.domain.model.PostEntity
import com.github.offlinefirstcrud.domain.repositories.PostsRepository
import javax.inject.Inject

class UpdatePostUseCase @Inject constructor(
    private val repository: PostsRepository
) {

    suspend fun build(postId: Int, postEntity: PostEntity) = repository.updatePost(postId, postEntity)
}
