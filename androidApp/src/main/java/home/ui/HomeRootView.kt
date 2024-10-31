package home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.stefan.lang.shapebyte.android.shared.ui.background.BackgroundViewWithHeaderAndList
import kotlinx.collections.immutable.toImmutableList

private const val DUMMY_LIST_COUNT = 100

@Composable
fun HomeRootView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        val items = List(DUMMY_LIST_COUNT) { "Item #$it" }.toImmutableList()

        BackgroundViewWithHeaderAndList(
            items = items,
            modifier = Modifier.fillMaxSize(),
            key = { it },
            contentType = { "Data" },
        ) { item ->
            Text(text = item)
        }
    }
}

@Preview
@Composable
fun HomeRootViewPreview() {
    HomeRootView()
}
