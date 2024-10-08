package de.stefan.lang.shapebyte.shared.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.stefan.lang.shapebyte.utils.Loggable
import de.stefan.lang.shapebyte.utils.Logging
import kotlinx.coroutines.CoroutineScope

actual open class BaseViewModel actual constructor(
    actual override val logger: Logging,
) : ViewModel(), Loggable {
    actual val scope: CoroutineScope = viewModelScope
}
