package de.stefan.lang.shapebyte.android.shared.preview.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import de.stefan.lang.coreutils.nativecontext.ContextProvider
import de.stefan.lang.shapebyte.android.designsystem.ui.ThemeData
import de.stefan.lang.shapebyte.android.designsystem.ui.With
import de.stefan.lang.shapebyte.app.data.mocks.PackageDependencyProviderMock
import de.stefan.lang.shapebyte.di.DPI
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatformTools

@Composable
fun PreviewContainer(
    modifier: Modifier = Modifier,
    content: @Composable (theme: ThemeData) -> Unit,
) {
    StartKoinIfNeeded()
    With { theme ->
        Box(modifier = modifier.background(theme.current.colorScheme.background)) {
            content(theme)
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

    DPI.setup(platformDependencyProvider)

    startKoin {
        androidContext(context)
        modules(DPI.testModules)
    }
}
