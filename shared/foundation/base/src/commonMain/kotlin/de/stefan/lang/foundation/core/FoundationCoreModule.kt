package de.stefan.lang.foundation.core

import de.stefan.lang.core.di.DIModuleDeclaration
import de.stefan.lang.foundation.core.stringformatter.DateTimeStringFormatter
import org.koin.core.component.get

interface FoundationCoreModuleProviding {
    fun dateTimeStringFormatter(): DateTimeStringFormatter
}

object FoundationCoreModule:
    DIModuleDeclaration(
        allEnvironments = {
            single<DateTimeStringFormatter> { DateTimeStringFormatter() }
        },
        appEnvironmentOnly = {
        },
        testEnvironmentOnly = {
        },
    ), FoundationCoreModuleProviding
{
    override fun dateTimeStringFormatter(): DateTimeStringFormatter {
        return get()
    }
}