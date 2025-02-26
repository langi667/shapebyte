package de.stefan.lang.navigation

import de.stefan.lang.foundationUI.event.UIEvent

interface NavigationRequest : UIEvent

data object NavigationRequestBack : NavigationRequest
