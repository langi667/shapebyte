package de.stefan.lang.shapebyte.utils.di

import de.stefan.lang.shapebyte.utils.assets.AssetLoading
import de.stefan.lang.shapebyte.utils.assets.impl.AssetLoader
import de.stefan.lang.shapebyte.utils.assets.mocks.AssetLoaderMock
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
import de.stefan.lang.shapebyte.utils.os.OperatingSystemInfoProviding
import org.koin.core.component.get

interface UtilsModuleProviding {
    fun logger(): Logging
    fun dimensionProvider(): DimensionProvider
    fun deviceInfoProvider(): DeviceInfoProviding
    fun dateTimeStringFormatter(): DateTimeStringFormatter
    fun assetLoader(): AssetLoading
}

object UtilsModule :
    DIModuleDeclaration(
        allEnvironments = {
            single<OperatingSystemInfoProviding> { get<DeviceInfoProviding>() }
            single<ScreenSizeProviding> { get<DeviceInfoProviding>() }
            single<DeviceSizeCategoryProviding> { DeviceSizeCategoryProvider(screenSizeProvider = get()) }
            single<DimensionProvider> { DimensionProvider(deviceSizeCategoryProvider = get()) }
            single<DateTimeStringFormatter> { DateTimeStringFormatter() }
        },
        appEnvironmentOnly = {
            single<AssetLoading> { AssetLoader(logging = get()) } // AssetLoaderMock
            single<DeviceInfoProviding> { DeviceInfo() }
            single<Logging> { Logger() }
        },
        testEnvironmentOnly = {
            single<AssetLoading> { AssetLoaderMock() }
            single<Logging> { SilentLogger() }
            single<DeviceInfoProviding> { DeviceInfoMock() }
        },
    ),
    UtilsModuleProviding {
    override fun logger(): Logging = get()
    override fun dimensionProvider(): DimensionProvider = get()
    override fun dateTimeStringFormatter(): DateTimeStringFormatter = get()

    override fun deviceInfoProvider(): DeviceInfoProviding = get()
    override fun assetLoader(): AssetLoading = get()
}
