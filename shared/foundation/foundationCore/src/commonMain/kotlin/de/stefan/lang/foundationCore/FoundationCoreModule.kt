package de.stefan.lang.foundationCore

import de.stefan.lang.coreutils.di.DIModuleDeclaration
import de.stefan.lang.foundationCore.FoundationCoreModule.appResourceProvider
import de.stefan.lang.foundationCore.assets.FileAssetLoading
import de.stefan.lang.foundationCore.assets.impl.FileAssetLoader
import de.stefan.lang.foundationCore.assets.mocks.FileAssetLoaderMock
import de.stefan.lang.foundationCore.audio.AudioPlaying
import de.stefan.lang.foundationCore.audio.impl.AudioPlayer
import de.stefan.lang.foundationCore.audio.mocks.AudioPlayerMock
import de.stefan.lang.foundationCore.device.deviceinfo.DeviceInfo
import de.stefan.lang.foundationCore.device.deviceinfo.DeviceInfoProviding
import de.stefan.lang.foundationCore.device.deviceinfo.mocks.DeviceInfoMock
import de.stefan.lang.foundationCore.device.devicesize.DeviceSizeCategoryProvider
import de.stefan.lang.foundationCore.device.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.foundationCore.device.devicesize.ScreenSizeProviding
import de.stefan.lang.foundationCore.device.safearea.SafeAreaDetector
import de.stefan.lang.foundationCore.os.OperatingSystemInfoProviding
import de.stefan.lang.foundationCore.resources.impl.AppResourceProvider
import de.stefan.lang.foundationCore.stringformatter.DateTimeStringFormatter
import org.koin.core.component.get

interface FoundationCoreModuleProviding {
    fun dateTimeStringFormatter(): DateTimeStringFormatter
    fun fileAssetLoader(): FileAssetLoading
    fun audioPlayer(): AudioPlaying
    fun deviceInfoProvider(): DeviceInfoProviding
}

object FoundationCoreModule :
    DIModuleDeclaration(
        allEnvironments = {
            single<OperatingSystemInfoProviding> { get<DeviceInfoProviding>() }
            single<ScreenSizeProviding> { get<DeviceInfoProviding>() }
            single<DeviceSizeCategoryProviding> { DeviceSizeCategoryProvider(screenSizeProvider = get()) }
            single<AppResourceProvider> { appResourceProvider }

            single<SafeAreaDetector> {
                SafeAreaDetector(logger = get())
            }

            single<DateTimeStringFormatter> { DateTimeStringFormatter() }
        },
        appEnvironmentOnly = {
            single<FileAssetLoading> {
                FileAssetLoader(logging = get(), appContextProvider = get())
            }

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
            factory<AudioPlaying> { AudioPlayerMock() }
            single<DeviceInfoProviding> { DeviceInfoMock() }
        },
    ),
    FoundationCoreModuleProviding {
    private lateinit var appResourceProvider: AppResourceProvider

    fun initialize(appResourceProvider: AppResourceProvider) {
        this.appResourceProvider = appResourceProvider
    }

    override fun dateTimeStringFormatter(): DateTimeStringFormatter = get()
    override fun fileAssetLoader(): FileAssetLoading = get()
    override fun audioPlayer(): AudioPlaying = get()
    override fun deviceInfoProvider(): DeviceInfoProviding = get()
}
