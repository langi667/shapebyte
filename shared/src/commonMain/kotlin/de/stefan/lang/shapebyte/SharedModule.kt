package de.stefan.lang.shapebyte

import de.stefan.lang.foundationCore.api.platformdependencies.PlatformDependencyProviding
import de.stefan.lang.shapebyte.di.BaseSharedModule

expect object SharedModule : BaseSharedModule {
    override fun start(platformDependencies: PlatformDependencyProviding)
}
