package de.stefan.lang.shapebyte.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import de.stefan.lang.shapebyte.android.workout.item.ui.CountdownItemSetsView
import de.stefan.lang.shapebyte.utils.Loggable
import de.stefan.lang.shapebyte.utils.Logging
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity(), Loggable {
    override val logger: Logging by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            ApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    CountdownItemSetsView(getViewModel())
                }
            }
        }
    }
}

@Composable
fun GreetingView(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(modifier = modifier, text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    ApplicationTheme {
    }
}
