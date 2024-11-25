package de.stefan.lang.shapebyte.utils.di

import de.stefan.lang.shapebyte.utils.assets.FileAssetLoading
import de.stefan.lang.shapebyte.utils.assets.impl.FileAssetLoader
import de.stefan.lang.shapebyte.utils.assets.mocks.FileAssetLoaderMock
import de.stefan.lang.shapebyte.utils.datetime.DateTimeStringFormatter
import de.stefan.lang.shapebyte.utils.device.deviceinfo.DeviceInfo
import de.stefan.lang.shapebyte.utils.device.deviceinfo.DeviceInfoProviding
import de.stefan.lang.shapebyte.utils.device.deviceinfo.mocks.DeviceInfoMock
import de.stefan.lang.shapebyte.utils.device.devicesize.DeviceSizeCategoryProvider
import de.stefan.lang.shapebyte.utils.device.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.shapebyte.utils.device.devicesize.ScreenSizeProviding
import de.stefan.lang.shapebyte.utils.device.safearea.SafeAreaDetector
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
    fun fileAssetLoader(): FileAssetLoading
}

object UtilsModule :
    DIModuleDeclaration(
        allEnvironments = {
            single<OperatingSystemInfoProviding> { get<DeviceInfoProviding>() }
            single<ScreenSizeProviding> { get<DeviceInfoProviding>() }
            single<DeviceSizeCategoryProviding> { DeviceSizeCategoryProvider(screenSizeProvider = get()) }
            single<DimensionProvider> { DimensionProvider(deviceSizeCategoryProvider = get()) }
            single<DateTimeStringFormatter> { DateTimeStringFormatter() }
            single<SafeAreaDetector> { SafeAreaDetector(logger = get()) }
        },
        appEnvironmentOnly = {
            single<FileAssetLoading> { FileAssetLoader(logging = get()) }
            single<DeviceInfoProviding> { DeviceInfo(safeAreaDetector = get()) }
            single<Logging> { Logger() }
        },
        testEnvironmentOnly = {
            single<FileAssetLoading> { FileAssetLoaderMock() }
            single<Logging> { SilentLogger() }
            single<DeviceInfoProviding> { DeviceInfoMock() }
        },
    ),
    UtilsModuleProviding {
    override fun logger(): Logging = get()
    override fun dimensionProvider(): DimensionProvider = get()
    override fun dateTimeStringFormatter(): DateTimeStringFormatter = get()

    override fun deviceInfoProvider(): DeviceInfoProviding = get()
    override fun fileAssetLoader(): FileAssetLoading = get()
}
