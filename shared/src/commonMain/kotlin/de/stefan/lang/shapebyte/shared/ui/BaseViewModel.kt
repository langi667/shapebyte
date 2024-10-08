package de.stefan.lang.shapebyte.shared.ui

import de.stefan.lang.shapebyte.utils.Loggable
import de.stefan.lang.shapebyte.utils.Logging
import kotlinx.coroutines.CoroutineScope

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect open class BaseViewModel(logger: Logging) : Loggable {
    val scope: CoroutineScope
    override val logger: Logging
}
