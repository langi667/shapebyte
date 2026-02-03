package de.stefan.lang.designsystem

import de.stefan.lang.core.di.RootModule
import de.stefan.lang.designsystem.contract.DesignSystemContract

public object DesignSystemModule :
    RootModule(
        dependencies = emptyList(),
    ),
    DesignSystemContract
