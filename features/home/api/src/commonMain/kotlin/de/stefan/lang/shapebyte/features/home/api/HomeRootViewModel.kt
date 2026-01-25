package de.stefan.lang.shapebyte.features.home.api

import de.stefan.lang.coreutils.contract.logging.Logging
import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.foundationPresentation.api.viewmodel.BaseViewModel

abstract class HomeRootViewModel(
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
) : BaseViewModel(logger, coroutineContextProvider) {
    abstract fun intent(intent: HomeRootUIIntent)
}
