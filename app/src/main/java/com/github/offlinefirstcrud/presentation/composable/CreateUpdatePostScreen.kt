package com.github.offlinefirstcrud.presentation.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.offlinefirstcrud.R
import com.github.offlinefirstcrud.domain.typealiases.Action
import com.github.offlinefirstcrud.presentation.activity.MainActivity
import com.github.offlinefirstcrud.presentation.theme.OfflineFirstCRUDTheme
import com.github.offlinefirstcrud.presentation.viewmodel.uimodel.PostUiModel

@Composable
fun CreateUpdatePostScreen(
    postUiModel: PostUiModel? = null,
    createUpdateAction: Action? = null
) {
    val context = LocalContext.current as MainActivity
    val postsViewModel = context.postsViewModel
    var titleTextFieldValue by remember { mutableStateOf(postUiModel?.title.orEmpty()) }
    var bodyTextFieldValue by remember { mutableStateOf(postUiModel?.body.orEmpty()) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = titleTextFieldValue,
            onValueChange = {
                titleTextFieldValue = it
            },
            shape = RoundedCornerShape(8.dp),
            label = {
                Text(
                    stringResource(R.string.create_post_label_title),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Normal
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.titleMedium,
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = bodyTextFieldValue,
            onValueChange = {
                bodyTextFieldValue = it
            },
            shape = RoundedCornerShape(8.dp),
            label = {
                Text(
                    stringResource(R.string.create_post_label_body),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Normal
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.bodyLarge,
            minLines = 10,
            maxLines = 10
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = titleTextFieldValue.isNotBlank() && bodyTextFieldValue.isNotBlank(),
            onClick = {
                if (postUiModel == null) {
                    postsViewModel.addPost(
                        PostUiModel(
                            id = postsViewModel.currentPostsCount.plus(1),
                            title = titleTextFieldValue,
                            body = bodyTextFieldValue
                        )
                    )
                } else {
                    postsViewModel.updatePost(postUiModel.copy(title = titleTextFieldValue, body = bodyTextFieldValue))
                }
                createUpdateAction?.invoke()
            },
            shape = RoundedCornerShape(100.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(if (postUiModel == null) R.string.create_post_button_create else R.string.create_post_button_update),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun CreateUpdatePostScreenPreview() {
    OfflineFirstCRUDTheme {
        CreateUpdatePostScreen()
    }
}
