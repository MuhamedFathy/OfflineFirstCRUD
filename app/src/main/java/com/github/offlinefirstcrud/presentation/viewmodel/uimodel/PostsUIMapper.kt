package com.github.offlinefirstcrud.presentation.viewmodel.uimodel

import com.github.offlinefirstcrud.domain.model.PostEntity

fun PostEntity.toUIModel() = PostUiModel(
    id = id,
    title = title,
    body = body
)

fun PostUiModel.toEntity() = PostEntity(
    id = id,
    title = title,
    body = body
)
