package de.stefan.lang.shapebyte.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import de.stefan.lang.shapebyte.android.features.home.ui.HomeRootView
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import org.koin.android.ext.android.inject

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
                    AppView()
                }
            }
        }
    }
}

@Composable
fun AppView() {
    HomeRootView()
}

@Preview
@Composable
fun DefaultPreview() {
    ApplicationTheme {
        AppView()
    }
}
