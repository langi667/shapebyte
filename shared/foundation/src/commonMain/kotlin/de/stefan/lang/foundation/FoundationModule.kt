package de.stefan.lang.foundation

import de.stefan.lang.coreutils.di.RootDIModule
import de.stefan.lang.foundationCore.FoundationCoreModule
import de.stefan.lang.foundationCore.FoundationCoreModuleProviding
import de.stefan.lang.foundationUI.FoundationUIModule
import de.stefan.lang.foundationUI.FoundationUIModuleProviding

interface FoundationModuleProviding : FoundationCoreModuleProviding, FoundationUIModuleProviding

object FoundationModule :
    RootDIModule(
        listOf(
            FoundationCoreModule,
            FoundationUIModule,
        ),
    ),
    FoundationCoreModuleProviding by FoundationCoreModule,
    FoundationUIModuleProviding by FoundationUIModule,
    FoundationModuleProviding
