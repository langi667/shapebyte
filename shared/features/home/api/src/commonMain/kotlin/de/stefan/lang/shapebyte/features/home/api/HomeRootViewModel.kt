package de.stefan.lang.shapebyte.features.home.api

import de.stefan.lang.coreutils.api.logging.Logging
import de.stefan.lang.coroutines.api.CoroutineContextProviding
import de.stefan.lang.foundationUi.api.viewmodel.BaseViewModel

abstract class HomeRootViewModel(
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
): BaseViewModel(logger, coroutineContextProvider) {
    abstract fun intent(intent: HomeRootUIIntent)
}
