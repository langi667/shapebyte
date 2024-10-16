package de.stefan.lang.shapebyte.shared.viewmodel.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.CoroutineScope

actual abstract class BaseViewModel actual constructor(
    actual override val logger: Logging,
) : ViewModel(), Loggable, UIStateProvider {
    actual val scope: CoroutineScope = viewModelScope
}
