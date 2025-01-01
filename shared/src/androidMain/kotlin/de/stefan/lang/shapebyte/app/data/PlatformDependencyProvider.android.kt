package de.stefan.lang.shapebyte.app.data

import android.content.Context
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineContextProviding
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineScopeProviding

actual data class PlatformDependencyProvider(
    val applicationContext: Context,
    actual override val coroutineScopeProviding: CoroutineScopeProviding,
    actual override val coroutineContextProvider: CoroutineContextProviding,
) : PlatformDependencyProviding
