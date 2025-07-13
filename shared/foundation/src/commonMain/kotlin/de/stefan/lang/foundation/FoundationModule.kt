package de.stefan.lang.foundation

import de.stefan.lang.core.di.RootDIModule
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.foundationCore.api.resources.AppResourceProvider
import de.stefan.lang.foundationUI.FoundationUIModule

object FoundationModule :
    RootDIModule(
        listOf(
            FoundationCoreModule,
            FoundationUIModule,
        ),
    ) {
    fun initialize(appResourceProvider: AppResourceProvider) {
        FoundationCoreModule.initialize(appResourceProvider)
    }
}
