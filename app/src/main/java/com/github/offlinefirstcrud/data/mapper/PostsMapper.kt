package com.github.offlinefirstcrud.data.mapper

import com.github.offlinefirstcrud.data.datasource.local.entity.PostDBEntity
import com.github.offlinefirstcrud.data.datasource.remote.models.PostsResponse
import com.github.offlinefirstcrud.domain.model.PostEntity

fun PostsResponse.toDBEntity() = PostDBEntity(
    id = id,
    title = title,
    body = body
)

fun PostEntity.toResponse() = PostsResponse(
    id = id,
    title = title,
    body = body
)

fun PostDBEntity.toDBEntity() = PostEntity(
    id = id,
    title = title,
    body = body
)

fun PostEntity.toDBEntity() = PostDBEntity(
    id = id,
    title = title,
    body = body
)
