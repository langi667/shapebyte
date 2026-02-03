package de.stefan.lang.shapebyte.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import de.stefan.lang.designsystem.theme.ShapeByteTheme
import de.stefan.lang.shapebyte.SharedModule
import de.stefan.lang.shapebyte.android.navigation.NavigationView
import de.stefan.lang.shapebyte.initializing.AppInitializationState
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity :
    ComponentActivity(),
    Loggable {
    override val logger: Logger by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        lifecycleScope.launch {
            SharedModule.sharedInitializationUseCase().flow.collectLatest {
                if (it == AppInitializationState.INITIALIZED) {
                    showMainScreen()
                }
            }
        }
    }

    private fun showMainScreen() {
        this.setContent {
            ShapeByteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    NavigationView()
                }
            }
        }
    }
}
