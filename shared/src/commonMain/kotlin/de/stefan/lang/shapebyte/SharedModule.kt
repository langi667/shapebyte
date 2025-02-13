package de.stefan.lang.shapebyte

import de.stefan.lang.shapebyte.di.BaseSharedModule
import de.stefan.lang.shapebyte.initializing.PlatformDependencyProvider

expect object SharedModule : BaseSharedModule {
    override fun start(platformDependencies: PlatformDependencyProvider)
}
