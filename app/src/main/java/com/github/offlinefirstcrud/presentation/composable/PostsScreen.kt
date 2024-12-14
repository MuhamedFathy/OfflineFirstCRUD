package com.github.offlinefirstcrud.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.offlinefirstcrud.R
import com.github.offlinefirstcrud.domain.typealiases.Consumer
import com.github.offlinefirstcrud.presentation.activity.MainActivity
import com.github.offlinefirstcrud.presentation.composable.navigation.NavRoutes
import com.github.offlinefirstcrud.presentation.holder.DataHolder
import com.github.offlinefirstcrud.presentation.theme.OfflineFirstCRUDTheme
import com.github.offlinefirstcrud.presentation.viewmodel.uimodel.PostUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(
    itemCallback: Consumer<PostUiModel> = {},
    editCallback: Consumer<PostUiModel> = {}
) {
    val context = LocalContext.current as MainActivity
    val postsViewModel = context.postsViewModel
    val postsState by postsViewModel.postsState.collectAsStateWithLifecycle()
    val scrollToBottom by postsViewModel.postAdded.collectAsStateWithLifecycle()

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    val refreshState = rememberPullToRefreshState()
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = Unit) {
        postsViewModel.loadPosts()
    }

    LaunchedEffect(key1 = scrollToBottom) {
        if (scrollToBottom) listState.scrollToItem(listState.layoutInfo.totalItemsCount)
    }

    PullToRefreshBox(
        modifier = Modifier
            .fillMaxSize(),
        state = refreshState,
        isRefreshing = refreshing,
        onRefresh = {
            refreshScope.launch {
                refreshing = true
                postsViewModel.loadPosts(isRefresh = true)
                delay(1000)
                refreshing = false
            }
        },
        contentAlignment = Alignment.Center
    ) {
        when (postsState) {
            is DataHolder.Loading -> {
                CircularProgressIndicator(modifier = Modifier.size(60.dp))
            }

            is DataHolder.Success -> {
                val posts = (postsState as DataHolder.Success).data

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState
                ) {
                    items(
                        items = posts,
                        key = { post -> post.id }
                    ) { post ->
                        PostItem(
                            postUiModel = post,
                            itemCallback = itemCallback,
                            editCallback = { editCallback(it) },
                            deleteCallback = { postsViewModel.deletePost(it) }
                        )

                        HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
                    }

                    item { Spacer(modifier = Modifier.height(100.dp)) }
                }
            }

            else -> {
                Text(
                    modifier = Modifier.padding(40.dp),
                    text = stringResource(R.string.general_error_title),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun LazyItemScope.PostItem(
    postUiModel: PostUiModel,
    itemCallback: Consumer<PostUiModel>,
    editCallback: Consumer<PostUiModel>,
    deleteCallback: Consumer<Int>
) {
    Column(
        modifier = Modifier
            .animateItem()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple()
            ) { itemCallback(postUiModel) }
            .padding(16.dp)
    ) {
        Text(
            text = postUiModel.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = postUiModel.body,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = { editCallback(postUiModel) }
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Edit,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Edit"
                )
            }

            IconButton(
                onClick = { deleteCallback(postUiModel.id) }
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Delete,
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = "Delete"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostScreenPreview() {
    OfflineFirstCRUDTheme {
        PostsScreen()
    }
}
