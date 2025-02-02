package de.stefan.lang.core

import de.stefan.lang.core.app.AppContextProvider
import de.stefan.lang.core.assets.FileAssetLoading
import de.stefan.lang.core.assets.impl.FileAssetLoader
import de.stefan.lang.core.assets.mocks.FileAssetLoaderMock
import de.stefan.lang.core.audio.AudioPlaying
import de.stefan.lang.core.audio.impl.AudioPlayer
import de.stefan.lang.core.audio.mocks.AudioPlayerMock
import de.stefan.lang.core.coroutines.CoroutineContextProviding
import de.stefan.lang.core.coroutines.CoroutineScopeProviding
import de.stefan.lang.core.device.deviceinfo.DeviceInfo
import de.stefan.lang.core.device.deviceinfo.DeviceInfoProviding
import de.stefan.lang.core.device.deviceinfo.mocks.DeviceInfoMock
import de.stefan.lang.core.device.devicesize.DeviceSizeCategoryProvider
import de.stefan.lang.core.device.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.core.device.devicesize.ScreenSizeProviding
import de.stefan.lang.core.device.safearea.SafeAreaDetector
import de.stefan.lang.core.logging.impl.Logger
import de.stefan.lang.core.logging.Logging
import de.stefan.lang.core.logging.mocks.SilentLogger
import de.stefan.lang.core.di.DIModuleDeclaration
import de.stefan.lang.core.dimension.DimensionProvider
import de.stefan.lang.core.os.OperatingSystemInfoProviding
import de.stefan.lang.core.resources.impl.AppResourceProvider
import org.koin.core.component.get

interface CoreModuleProviding {
    fun logger(): Logging
    fun deviceInfoProvider(): DeviceInfoProviding
    fun dimensionProvider(): DimensionProvider
    fun fileAssetLoader(): FileAssetLoading
    fun audioPlayer(): AudioPlaying
    fun coroutineContextProvider(): CoroutineContextProviding
    fun coroutineScopeProvider(): CoroutineScopeProviding
}

object CoreModule: DIModuleDeclaration(
    allEnvironments = {
        single<OperatingSystemInfoProviding> { get<DeviceInfoProviding>() }
        single<ScreenSizeProviding> { get<DeviceInfoProviding>() }
        single<DeviceSizeCategoryProviding> { DeviceSizeCategoryProvider(screenSizeProvider = get()) }

        single<SafeAreaDetector> { SafeAreaDetector(logger = get()) }
        single<DimensionProvider> { DimensionProvider(deviceSizeCategoryProvider = get()) }

        single<AppContextProvider> { CoreModule.appContextProvider }
        single<AppResourceProvider> { CoreModule.appResourceProvider }

        single<CoroutineScopeProviding> { CoreModule.coroutineScopeProvider }
        single<CoroutineContextProviding> { CoreModule.coroutineContextProvider }
        single<CoroutineScopeProviding> { CoreModule.coroutineScopeProvider }
    },
    appEnvironmentOnly = {
        single<FileAssetLoading> { FileAssetLoader(logging = get(), appContextProvider = get()) }
        single<Logging> { Logger() }
        single<DeviceInfoProviding> { DeviceInfo(safeAreaDetector = get()) }
        factory<AudioPlaying> {
            AudioPlayer(
                appContextProvider = get(),
                appResourceProvider = get(),
                logger = get(),
            )
        }
    },
    testEnvironmentOnly = {
        single<FileAssetLoading> { FileAssetLoaderMock() }
        single<Logging> { SilentLogger() }
        single<DeviceInfoProviding> { DeviceInfoMock() }
        factory<AudioPlaying> { AudioPlayerMock() }
    }
),
    CoreModuleProviding
{
    private lateinit var appContextProvider: AppContextProvider
    private lateinit var appResourceProvider: AppResourceProvider

    private lateinit var coroutineContextProvider: CoroutineContextProviding
    private lateinit var coroutineScopeProvider: CoroutineScopeProviding

    fun initialize(
        coroutineContextProvider: CoroutineContextProviding,
        coroutineScopeProviding: CoroutineScopeProviding,
        appContextProvider: AppContextProvider,
        appResourceProvider: AppResourceProvider
    ) {
        this.appContextProvider = appContextProvider
        this.appResourceProvider = appResourceProvider
        this.coroutineContextProvider = coroutineContextProvider
        this.coroutineScopeProvider = coroutineScopeProviding
    }

    override fun logger(): Logging = get()
    override fun deviceInfoProvider(): DeviceInfoProviding = get()
    override fun dimensionProvider(): DimensionProvider = get()

    override fun audioPlayer(): AudioPlaying = get()
    override fun fileAssetLoader(): FileAssetLoading = get()

    override fun coroutineContextProvider(): CoroutineContextProviding = get()
    override fun coroutineScopeProvider(): CoroutineScopeProviding = get()
}