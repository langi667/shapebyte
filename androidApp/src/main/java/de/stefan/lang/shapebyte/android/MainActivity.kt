package de.stefan.lang.shapebyte.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.stefan.lang.coreutils.logging.Loggable
import de.stefan.lang.coreutils.logging.Logging
import de.stefan.lang.shapebyte.SharedModule
import de.stefan.lang.shapebyte.android.designsystem.ui.With
import de.stefan.lang.shapebyte.android.features.home.ui.HomeRootView
import de.stefan.lang.shapebyte.android.features.workout.timed.ui.TimedWorkoutView
import de.stefan.lang.shapebyte.android.navigation.NavRoute
import de.stefan.lang.shapebyte.android.navigation.workoutIdOr
import de.stefan.lang.shapebyte.initializing.SharedInitializationState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity :
    ComponentActivity(),
    Loggable {
    override val logger: Logging by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        lifecycleScope.launch {
            SharedModule.sharedInitializationUseCase().flow.collectLatest {
                if (it == SharedInitializationState.INITIALIZED) {
                    showMainScreen()
                }
            }
        }
    }

    private fun showMainScreen() {
        this.setContent {
            With { theme ->
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = theme.current.colorScheme.background,
                ) {
                    AppView()
                }
            }
        }
    }

    @Composable
    fun AppView(modifier: Modifier = Modifier) {
        val startDestination = NavRoute.startDestination.path
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = startDestination,
        ) {
            NavRoute.entries.forEach {
                when (it) {
                    NavRoute.HomeRoot -> {
                        composable(it.path) {
                            HomeRootView(navController, modifier)
                        }
                    }

                    NavRoute.QuickWorkout -> {
                        composable(it.path) { backStackEntry ->
                            val itemId = backStackEntry.arguments.workoutIdOr("-1").toInt()
                            TimedWorkoutView(itemId, navController, modifier.fillMaxSize())
                        }
                    }
                }
            }
        }
    }
}
