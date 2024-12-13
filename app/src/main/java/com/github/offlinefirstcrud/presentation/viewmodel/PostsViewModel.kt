package com.github.offlinefirstcrud.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.offlinefirstcrud.presentation.holder.DataHolder
import com.github.offlinefirstcrud.domain.interactor.AddPostUseCase
import com.github.offlinefirstcrud.domain.interactor.DeletePostUseCase
import com.github.offlinefirstcrud.domain.interactor.GetPostsUseCase
import com.github.offlinefirstcrud.domain.interactor.UpdatePostUseCase
import com.github.offlinefirstcrud.presentation.viewmodel.uimodel.PostUiModel
import com.github.offlinefirstcrud.presentation.viewmodel.uimodel.toEntity
import com.github.offlinefirstcrud.presentation.viewmodel.uimodel.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val addPostUseCase: AddPostUseCase,
    private val updatePostUseCase: UpdatePostUseCase,
    private val deletePostUseCase: DeletePostUseCase
) : ViewModel() {

    private val _postsState = MutableStateFlow<DataHolder<List<PostUiModel>>>(value = DataHolder.Loading)
    val postsState = _postsState.asStateFlow()

    private var hasLoadedData = false

    fun loadPosts(isRefresh: Boolean = false) {
        if (!isRefresh) {
            if (hasLoadedData) return
            hasLoadedData = true
        }
        viewModelScope.launch {
            getPostsUseCase.build()
                .catch { throwable ->
                    _postsState.value = DataHolder.Fail(throwable)
                }
                .collect { posts ->
                    _postsState.value = DataHolder.Success(posts.map { postEntity -> postEntity.toUIModel() })
                }
        }
    }

    fun addPost(postUiModel: PostUiModel) {
        viewModelScope.launch {
            addPostUseCase.build(postUiModel.toEntity())
                .catch { throwable ->
                    _postsState.value = DataHolder.Fail(throwable)
                }
                .collect { posts ->
                    _postsState.value = DataHolder.Success(posts.map { postEntity -> postEntity.toUIModel() })
                }
        }
    }

    fun updatePost(postId: Int, post: PostUiModel) {
        viewModelScope.launch {
            updatePostUseCase.build(postId, post.toEntity())
                .catch { throwable ->
                    _postsState.value = DataHolder.Fail(throwable)
                }
                .collect { posts ->
                    _postsState.value = DataHolder.Success(posts.map { postEntity -> postEntity.toUIModel() })
                }
        }
    }

    fun deletePost(postId: Int) {
        viewModelScope.launch {
            deletePostUseCase.build(postId)
                .catch { throwable ->
                    _postsState.value = DataHolder.Fail(throwable)
                }
                .collect { posts ->
                    _postsState.value = DataHolder.Success(posts.map { postEntity -> postEntity.toUIModel() })
                }
        }
    }
}
