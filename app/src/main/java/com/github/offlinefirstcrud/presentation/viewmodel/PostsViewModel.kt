package com.github.offlinefirstcrud.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.github.offlinefirstcrud.data.worker.PostsSynchronizationWorker
import com.github.offlinefirstcrud.domain.interactor.AddPostUseCase
import com.github.offlinefirstcrud.domain.interactor.DeletePostUseCase
import com.github.offlinefirstcrud.domain.interactor.GetPostsUseCase
import com.github.offlinefirstcrud.domain.interactor.UpdatePostUseCase
import com.github.offlinefirstcrud.presentation.holder.DataHolder
import com.github.offlinefirstcrud.presentation.viewmodel.uimodel.PostUiModel
import com.github.offlinefirstcrud.presentation.viewmodel.uimodel.toEntity
import com.github.offlinefirstcrud.presentation.viewmodel.uimodel.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getPostsUseCase: GetPostsUseCase,
    private val addPostUseCase: AddPostUseCase,
    private val updatePostUseCase: UpdatePostUseCase,
    private val deletePostUseCase: DeletePostUseCase
) : ViewModel() {

    private val _postsState = MutableStateFlow<DataHolder<List<PostUiModel>>>(value = DataHolder.Loading)
    val postsState = _postsState.asStateFlow()

    var postAdded = MutableStateFlow(false)

    var currentPostsCount = 0

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
                    currentPostsCount = posts.size
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
                    currentPostsCount = posts.size
                    postAdded.value = true
                    delay(300)
                    postAdded.value = false
                }
        }
    }

    fun updatePost(post: PostUiModel) {
        viewModelScope.launch {
            updatePostUseCase.build(post.toEntity())
                .catch { throwable ->
                    _postsState.value = DataHolder.Fail(throwable)
                }
                .collect { posts ->
                    _postsState.value = DataHolder.Success(posts.map { postEntity -> postEntity.toUIModel() })
                    currentPostsCount = posts.size
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
                    currentPostsCount = posts.size
                }
        }
    }

    fun syncPosts() {
        val periodicWorkRequest =
            PeriodicWorkRequestBuilder<PostsSynchronizationWorker>(repeatInterval = 1, repeatIntervalTimeUnit = TimeUnit.HOURS)
                .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build()

        val workManager = WorkManager.getInstance(context)

        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName = "CRUD_PostsSynchronizationWorker",
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            request = periodicWorkRequest
        )

        viewModelScope.launch {
            workManager.getWorkInfoByIdFlow(periodicWorkRequest.id)
                .filterNotNull()
                .map {
                    if (it.state == WorkInfo.State.RUNNING) {
                        withContext(Dispatchers.Main) {
                            _postsState.value = DataHolder.Loading
                            loadPosts(isRefresh = true)
                        }
                    }
                    it
                }
                .collect { workInfo ->
                    Log.e(PostsViewModel::class.java.simpleName, " workManager state ${workInfo.state}")
                }
        }
    }
}
