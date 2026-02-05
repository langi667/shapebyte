package de.stefan.lang.shapebyte.features.home.presentation.contract

import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestHandler

public interface HomePresentationContract {
    public fun homeRootViewModel(
        navigationHandler: NavigationRequestHandler,
    ): HomeRootViewModel
}
