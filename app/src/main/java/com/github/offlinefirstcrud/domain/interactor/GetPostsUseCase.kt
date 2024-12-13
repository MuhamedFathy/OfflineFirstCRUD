package com.github.offlinefirstcrud.domain.interactor

import com.github.offlinefirstcrud.domain.repositories.PostsRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: PostsRepository
) {

    suspend fun build() = repository.getPosts()
}
