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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.offlinefirstcrud.domain.typealiases.Action
import com.github.offlinefirstcrud.presentation.theme.OfflineFirstCRUDTheme

@Composable
fun PostsScreen(
    count: Int = 2,
    itemCallback: Action = {}
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(
            count = count,
            key = { index -> index }
        ) { index ->
            PostItem(
                title = "Post Title",
                content = if (index % 2 == 0) "Lorem ipsum dolor sit amet, consectetur adipiscing elit." else "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam non viverra eros. Nullam facilisis sapien at urna mattis, non sollicitudin enim imperdiet. Nunc vulputate enim sapien, nec fermentum risus laoreet at.",
                itemCallback = itemCallback,
                editCallback = {},
                deleteCallback = {}
            )

            if (index < count - 1) {
                HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
            }
        }

        item { Spacer(modifier = Modifier.height(100.dp)) }
    }
}

@Composable
fun LazyItemScope.PostItem(
    title: String,
    content: String,
    itemCallback: Action,
    editCallback: Action,
    deleteCallback: Action
) {
    Column(
        modifier = Modifier
            .animateItem()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple()
            ) { itemCallback() }
            .padding(16.dp)
    ) {
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = content,
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
                onClick = { /* Handle edit click */ }
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Edit,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Edit"
                )
            }

            IconButton(
                onClick = { /* Handle delete click */ }
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
