package de.stefan.lang.shapebyte.shared.viewmodel.ui

import de.stefan.lang.shapebyte.utils.Loggable
import de.stefan.lang.shapebyte.utils.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect abstract class BaseViewModel(
    logger: Logging,
) : Loggable, UIStateProvider {
    val scope: CoroutineScope
    override val logger: Logging
    abstract override val state: StateFlow<UIState>
}
