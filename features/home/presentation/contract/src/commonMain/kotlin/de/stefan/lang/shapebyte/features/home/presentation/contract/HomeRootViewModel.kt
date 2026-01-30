package de.stefan.lang.shapebyte.features.home.presentation.contract

import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.foundation.presentation.contract.viewmodel.BaseViewModel
import de.stefan.lang.utils.logging.contract.Logging

public abstract class HomeRootViewModel(
    logger: Logging,
    coroutineContextProvider: CoroutineContextProviding,
) : BaseViewModel(logger, coroutineContextProvider) {
    public abstract fun intent(intent: HomeRootUIIntent)
}
