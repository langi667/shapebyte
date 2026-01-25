package de.stefan.lang.shapebyte.features.home.presentation.contract

import de.stefan.lang.coreutils.contract.logging.Logging
import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.foundation.presentation.contract.viewmodel.BaseViewModel

public abstract class HomeRootViewModel(
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
) : BaseViewModel(logger, coroutineContextProvider) {
    public abstract fun intent(intent: HomeRootUIIntent)
}
