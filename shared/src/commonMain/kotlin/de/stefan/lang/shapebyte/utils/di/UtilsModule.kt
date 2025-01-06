package de.stefan.lang.shapebyte.utils.di

import de.stefan.lang.shapebyte.utils.app.appinfo.AppInfo
import de.stefan.lang.shapebyte.utils.assets.FileAssetLoading
import de.stefan.lang.shapebyte.utils.assets.impl.FileAssetLoader
import de.stefan.lang.shapebyte.utils.assets.mocks.FileAssetLoaderMock
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineContextProviding
import de.stefan.lang.shapebyte.utils.coroutines.CoroutineScopeProviding
import de.stefan.lang.shapebyte.utils.datetime.DateTimeStringFormatter
import de.stefan.lang.shapebyte.utils.device.deviceinfo.DeviceInfo
import de.stefan.lang.shapebyte.utils.device.deviceinfo.DeviceInfoProviding
import de.stefan.lang.shapebyte.utils.device.deviceinfo.mocks.DeviceInfoMock
import de.stefan.lang.shapebyte.utils.device.devicesize.DeviceSizeCategoryProvider
import de.stefan.lang.shapebyte.utils.device.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.shapebyte.utils.device.devicesize.ScreenSizeProviding
import de.stefan.lang.shapebyte.utils.device.safearea.SafeAreaDetector
import de.stefan.lang.shapebyte.utils.di.UtilsModule.coroutineContextProvider
import de.stefan.lang.shapebyte.utils.di.UtilsModule.coroutineScopeProviding
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
    fun coroutineContextProvider(): CoroutineContextProviding
    fun coroutineScopeProvider(): CoroutineScopeProviding
    fun appInfo(): AppInfo
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
            single<CoroutineContextProviding> { coroutineContextProvider }
            single<CoroutineScopeProviding> { coroutineScopeProviding }
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

    private lateinit var coroutineContextProvider: CoroutineContextProviding
    private lateinit var coroutineScopeProviding: CoroutineScopeProviding
    private lateinit var appInfo: AppInfo

    fun initialize(
        coroutineContextProvider: CoroutineContextProviding,
        coroutineScopeProviding: CoroutineScopeProviding,
        appInfo: AppInfo,
    ) {
        this.coroutineContextProvider = coroutineContextProvider
        this.coroutineScopeProviding = coroutineScopeProviding
        this.appInfo = appInfo
    }

    override fun logger(): Logging = get()
    override fun dimensionProvider(): DimensionProvider = get()
    override fun dateTimeStringFormatter(): DateTimeStringFormatter = get()

    override fun deviceInfoProvider(): DeviceInfoProviding = get()
    override fun fileAssetLoader(): FileAssetLoading = get()
    override fun coroutineContextProvider(): CoroutineContextProviding = get()
    override fun coroutineScopeProvider(): CoroutineScopeProviding = get()
    override fun appInfo(): AppInfo = appInfo
}
