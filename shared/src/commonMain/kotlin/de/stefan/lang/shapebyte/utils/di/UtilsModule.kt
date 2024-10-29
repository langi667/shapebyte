package de.stefan.lang.shapebyte.utils.di

import de.stefan.lang.shapebyte.utils.datetime.DateTimeStringFormatter
import de.stefan.lang.shapebyte.utils.device.devicesize.DeviceSizeCategoryProvider
import de.stefan.lang.shapebyte.utils.device.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.shapebyte.utils.device.devicesize.ScreenSizeProvider
import de.stefan.lang.shapebyte.utils.device.devicesize.ScreenSizeProviding
import de.stefan.lang.shapebyte.utils.device.devicesize.mocks.ScreenSizeProviderMock
import de.stefan.lang.shapebyte.utils.dicore.DIModule
import de.stefan.lang.shapebyte.utils.dimension.DimensionProvider
import de.stefan.lang.shapebyte.utils.logging.Logger
import de.stefan.lang.shapebyte.utils.logging.Logging
import de.stefan.lang.shapebyte.utils.mocks.SilentLogger
import org.koin.core.component.get
import org.koin.dsl.module

interface UtilsModuleProviding {
    fun logger(): Logging
    fun dimensionProvider(): DimensionProvider
    fun dateTimeStringFormatter(): DateTimeStringFormatter
}

object UtilsModule : DIModule, UtilsModuleProviding {
    override val module = module {
        single<Logging> { Logger() }
        single<ScreenSizeProviding> { ScreenSizeProvider() }
        single<DeviceSizeCategoryProviding> { DeviceSizeCategoryProvider(screenSizeProvider = get()) }

        single<DimensionProvider> { DimensionProvider(deviceSizeCategoryProvider = get()) }
        single<DateTimeStringFormatter> { DateTimeStringFormatter() }
    }

    override val testModule = module {
        single<Logging> { SilentLogger() }
        single<ScreenSizeProviding> { ScreenSizeProviderMock() }
        single<DeviceSizeCategoryProvider> { DeviceSizeCategoryProvider(screenSizeProvider = get()) }

        single<DimensionProvider> { DimensionProvider(deviceSizeCategoryProvider = get()) }
        single<DateTimeStringFormatter> { DateTimeStringFormatter() }
    }

    override fun logger(): Logging = get()
    override fun dimensionProvider(): DimensionProvider = get()
    override fun dateTimeStringFormatter(): DateTimeStringFormatter = get()
}
