package com.github.offlinefirstcrud.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.offlinefirstcrud.presentation.theme.OfflineFirstCRUDTheme

@Composable
fun PostDetailsScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .background(color = MaterialTheme.colorScheme.inverseOnSurface, shape = RoundedCornerShape(26.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Post title",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tincidunt lacinia justo malesuada vulputate. Etiam ac mauris leo. Cras vestibulum varius nisl, nec elementum orci. Morbi vitae leo molestie tellus consectetur vulputate. Nunc aliquet sit amet risus ut volutpat. Suspendisse semper purus id enim ornare iaculis. Curabitur dapibus sollicitudin leo sollicitudin sagittis. Nunc id ipsum feugiat, dictum dui non, mattis tortor. Nunc lobortis lorem sit amet nibh dignissim, et consequat purus ultricies.\n" +
                    "\n" +
                    "Praesent ultrices diam eget accumsan interdum. Vestibulum turpis ipsum, dictum ac aliquet nec, porttitor sed nulla. Mauris malesuada massa molestie mattis mattis. Suspendisse sollicitudin dui eu aliquet fermentum. Etiam maximus, lorem ultricies varius convallis, ipsum eros laoreet nulla, at pretium metus nisi finibus diam. Ut pretium felis non fermentum sodales. Proin quam justo, blandit sit amet faucibus ut, mollis vitae erat. Phasellus eget vulputate justo. Sed luctus vitae nunc et maximus. Fusce metus enim, gravida ut molestie eget, auctor eget velit. Sed nec felis vel turpis pulvinar tincidunt nec quis nunc. Nam ornare mauris a ante vulputate pulvinar. Nam congue sapien pulvinar arcu ullamcorper, non faucibus neque sollicitudin. Maecenas nec venenatis lectus, sagittis condimentum odio. In a faucibus eros, eget tempus diam.\n" +
                    "\n" +
                    "In hac habitasse platea dictumst. Quisque ultricies dignissim justo eu accumsan. Phasellus vel libero egestas, pharetra sapien ac, tristique nisl. Nam egestas id enim non viverra. In ullamcorper maximus velit, aliquam consectetur tortor varius nec. Nulla in condimentum urna, porttitor tempor elit. Sed id finibus nulla. Suspendisse potenti. Phasellus ultrices hendrerit sapien nec tristique. Fusce sed pellentesque augue. Aliquam quis nisi purus. Nunc ultrices augue enim, ut commodo est ultricies vitae. Ut eleifend nisl non iaculis porttitor.\n" +
                    "\n" +
                    "Praesent ac mauris metus. Proin vehicula elementum velit sit amet venenatis. Integer vitae nibh tincidunt, aliquet lorem in, finibus sem. Donec quis gravida nisl. Suspendisse nec rutrum dui, in rutrum diam. Nunc ipsum urna, dignissim ac neque nec, dignissim tincidunt urna. Duis auctor ullamcorper orci eu auctor.\n" +
                    "\n" +
                    "Curabitur consequat libero scelerisque placerat blandit. Mauris vehicula placerat nunc, eu lacinia sem varius ut. Maecenas tincidunt malesuada pellentesque. Donec ullamcorper sem nulla, quis ultrices felis pulvinar at. Fusce pulvinar ipsum sodales ante ultrices ultrices. Integer iaculis orci sit amet sagittis dictum. Maecenas ut erat a sapien hendrerit vehicula sit amet et neque. Pellentesque vehicula, ante non vehicula finibus, diam sapien volutpat est, vitae condimentum augue quam a justo. Morbi vel massa enim. Praesent in ullamcorper mauris. Sed id nisl volutpat, porta risus eu, posuere magna. Donec augue dui, commodo sit amet sollicitudin vitae, bibendum et metus. Nulla maximus felis at diam gravida, ut semper nunc semper. Etiam fringilla malesuada leo, in hendrerit turpis. Praesent suscipit rhoncus congue. Curabitur mattis lorem eu nisl facilisis consectetur.\n" +
                    "\n" +
                    "Morbi vel augue sed velit pellentesque lacinia sed eget purus. Mauris finibus ex ut posuere convallis. Pellentesque in sem vel arcu vehicula dignissim et in nibh. Vivamus ornare semper ultrices. Quisque aliquet suscipit ante, vitae iaculis risus. Nulla lacinia sodales gravida. Donec fringilla elit at mauris vehicula viverra. Nullam varius sodales ipsum sit amet vestibulum. Sed volutpat enim in efficitur condimentum. Vestibulum consequat porta bibendum. Quisque aliquet nisl nisl, at euismod nisi vulputate eu. In placerat leo a justo fermentum, quis faucibus eros commodo. Aenean aliquam nulla eu nisl ornare ornare. Vivamus a erat et nibh fermentum mollis. Quisque neque nulla, sollicitudin a arcu non, feugiat congue orci.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PostDetailsScreenPreview() {
    OfflineFirstCRUDTheme {
        PostDetailsScreen()
    }
}
