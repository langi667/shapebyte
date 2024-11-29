package de.stefan.lang.shapebyte.android.shared.preview.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import de.stefan.lang.shapebyte.android.ApplicationTheme
import de.stefan.lang.shapebyte.android.designsystem.ui.With
import de.stefan.lang.shapebyte.di.DPI
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatformTools

@Composable
fun PreviewContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) = ApplicationTheme {
    StartKoinIfNeeded()
    Box(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        content()
    }
}

@Composable
private fun StartKoinIfNeeded() = With { _, _, log ->
    if (KoinPlatformTools.defaultContext().getOrNull() != null) {
        log.d("Koin Preview", "Koin already started")
        return@With
    }

    val context = LocalContext.current

    startKoin {
        androidContext(context)
        modules(DPI.testModules)
    }
}
