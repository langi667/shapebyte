package de.stefan.lang.shapebyte.features.home.presentation.contract

import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import de.stefan.lang.coroutines.contract.CoroutineScopeProvider
import de.stefan.lang.foundation.presentation.contract.viewmodel.BaseViewModel
import de.stefan.lang.foundation.presentation.contract.viewmodel.v2.SharedViewModelBase
import de.stefan.lang.utils.logging.contract.Logger

public abstract class HomeRootViewModelV2(
    logger: Logger,
    coroutineContextProvider: CoroutineContextProvider,
    coroutineScopeProvider: CoroutineScopeProvider
): SharedViewModelBase<HomeRootUIIntent>(logger, coroutineContextProvider, coroutineScopeProvider)