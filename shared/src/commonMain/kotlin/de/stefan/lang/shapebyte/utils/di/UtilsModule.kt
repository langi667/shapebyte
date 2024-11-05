package de.stefan.lang.shapebyte.utils.di

import de.stefan.lang.shapebyte.utils.datetime.DateTimeStringFormatter
import de.stefan.lang.shapebyte.utils.device.deviceinfo.DeviceInfo
import de.stefan.lang.shapebyte.utils.device.deviceinfo.DeviceInfoMock
import de.stefan.lang.shapebyte.utils.device.deviceinfo.DeviceInfoProviding
import de.stefan.lang.shapebyte.utils.device.devicesize.DeviceSizeCategoryProvider
import de.stefan.lang.shapebyte.utils.device.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.shapebyte.utils.device.devicesize.ScreenSizeProviding
import de.stefan.lang.shapebyte.utils.dicore.DIModuleDeclaration
import de.stefan.lang.shapebyte.utils.dimension.DimensionProvider
import de.stefan.lang.shapebyte.utils.logging.Logger
import de.stefan.lang.shapebyte.utils.logging.Logging
import de.stefan.lang.shapebyte.utils.mocks.SilentLogger
import org.koin.core.component.get

interface UtilsModuleProviding {
    fun logger(): Logging
    fun dimensionProvider(): DimensionProvider
    fun dateTimeStringFormatter(): DateTimeStringFormatter
}

object UtilsModule :
    DIModuleDeclaration(
        allEnvironments = {
            single<ScreenSizeProviding> { get<DeviceInfoProviding>() }
            single<DeviceSizeCategoryProviding> { DeviceSizeCategoryProvider(screenSizeProvider = get()) }
            single<DimensionProvider> { DimensionProvider(deviceSizeCategoryProvider = get()) }
            single<DateTimeStringFormatter> { DateTimeStringFormatter() }
        },
        appEnvironmentOnly = {
            single<DeviceInfoProviding> { DeviceInfo() }
            single<Logging> { Logger() }
        },
        testEnvironmentOnly = {
            single<Logging> { SilentLogger() }
            single<DeviceInfoProviding> { DeviceInfoMock() }
        },
    ),
    UtilsModuleProviding {
    override fun logger(): Logging = get()
    override fun dimensionProvider(): DimensionProvider = get()
    override fun dateTimeStringFormatter(): DateTimeStringFormatter = get()
}
