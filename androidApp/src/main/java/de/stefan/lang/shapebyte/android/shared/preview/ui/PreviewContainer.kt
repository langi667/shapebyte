package de.stefan.lang.shapebyte.android.shared.preview.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import de.stefan.lang.coreutils.api.nativecontext.ContextProvider
import de.stefan.lang.designsystem.theme.ShapeByteTheme
import de.stefan.lang.shapebyte.SharedModule
import de.stefan.lang.shapebyte.featureCore.platformdependencies.mocks.PackageDependencyProviderMock
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatformTools

@Composable
fun PreviewContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    StartKoinIfNeeded()
    ShapeByteTheme {
        Box(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
            content()
        }
    }
}

@Composable
private fun StartKoinIfNeeded() {
    if (KoinPlatformTools.defaultContext().getOrNull() != null) {
        return
    }

    val context = LocalContext.current
    val platformDependencyProvider = PackageDependencyProviderMock(
        appContextProvider = ContextProvider(context),
    )

    SharedModule.setup(platformDependencyProvider)

    startKoin {
        androidContext(context)
        modules(SharedModule.testModules)
    }
}
