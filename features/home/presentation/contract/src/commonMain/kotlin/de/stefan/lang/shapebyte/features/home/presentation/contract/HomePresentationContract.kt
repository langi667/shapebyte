package de.stefan.lang.shapebyte.features.home.presentation.contract

import de.stefan.lang.shapebyte.features.navigation.contract.NavigationRequestHandling

public interface HomePresentationContract {
    public fun homeRootViewModel(
        navigationHandler: NavigationRequestHandling,
    ): HomeRootViewModel
}
