package de.stefan.lang.foundation.presentation.contract.viewmodel

import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import de.stefan.lang.coroutines.contract.CoroutineScopeProvider
import de.stefan.lang.foundation.presentation.contract.event.UIEvent
import de.stefan.lang.foundation.presentation.contract.intent.UIIntent
import de.stefan.lang.utils.logging.contract.Logger

internal class TestSharedViewModelBase(
    logger: Logger,
    coroutineContextProvider: CoroutineContextProvider,
    coroutineScopeProvider: CoroutineScopeProvider,
    ) : SharedViewModelBase<UIIntent>(logger, coroutineContextProvider, coroutineScopeProvider) {
    override fun onIntent(intent: UIIntent) {
            lastIntent = intent
        }

        var lastIntent: UIIntent? = null
        var onClearedCallback: (() -> Unit)? = null

        override fun onCleared() {
            onClearedCallback?.invoke()
        }
    }

    internal object TestEvent : UIEvent