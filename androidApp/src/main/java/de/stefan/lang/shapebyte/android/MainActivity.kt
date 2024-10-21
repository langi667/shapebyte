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
import de.stefan.lang.shapebyte.android.workout.item.ui.CountdownItemSetsView
import de.stefan.lang.shapebyte.utils.di.UtilsModule
import de.stefan.lang.shapebyte.utils.dimension.DimensionProvider
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.getViewModel
import org.koin.core.component.get

class MainActivity : ComponentActivity(), Loggable {
    override val logger: Logging by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val test = UtilsModule.get<DimensionProvider>()

        // CommonMainModule.dimensionProvider
        print(test)
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
fun AppView() {
    CountdownItemSetsView(getViewModel())
}

@Preview
@Composable
fun DefaultPreview() {
    ApplicationTheme {
        AppView()
    }
}
