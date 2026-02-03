package de.stefan.lang.designsystem

import de.stefan.lang.core.di.RootModule
import de.stefan.lang.designsystem.contract.DesignSystemContract
import de.stefan.lang.shapebyte.designsystem.generated.Module

public object DesignSystemModule : Module(),
    DesignSystemContract
