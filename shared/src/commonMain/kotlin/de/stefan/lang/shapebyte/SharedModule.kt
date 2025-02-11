package de.stefan.lang.shapebyte

import de.stefan.lang.foundationCore.platform.PlatformDependencyProvider
import de.stefan.lang.shapebyte.di.BaseSharedModule

expect object SharedModule : BaseSharedModule {
    override fun start(platformDependencies: PlatformDependencyProvider)
}
