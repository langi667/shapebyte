package de.stefan.lang.foundationCore

import de.stefan.lang.core.di.DIModuleDeclaration
import de.stefan.lang.foundationCore.FoundationCoreModule.appResourceProvider
import de.stefan.lang.foundationCore.api.assets.FileAssetLoading
import de.stefan.lang.foundationCore.api.audio.AudioPlaying
import de.stefan.lang.foundationCore.api.deviceinfo.DeviceInfoProviding
import de.stefan.lang.foundationCore.api.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.foundationCore.api.devicesize.ScreenSizeProviding
import de.stefan.lang.foundationCore.api.os.OperatingSystemInfoProviding
import de.stefan.lang.foundationCore.api.resources.AppResourceProvider
import de.stefan.lang.foundationCore.api.stringformatter.DateTimeStringFormatter
import de.stefan.lang.foundationCore.impl.assets.FileAssetLoader
import de.stefan.lang.foundationCore.impl.audio.AudioPlayer
import de.stefan.lang.foundationCore.impl.deviceinfo.DeviceInfo
import de.stefan.lang.foundationCore.impl.devicesize.DeviceSizeCategoryProvider
import de.stefan.lang.foundationCore.impl.safearea.SafeAreaDetector
import de.stefan.lang.foundationCore.test.assets.FileAssetLoaderMock
import de.stefan.lang.foundationCore.test.audio.AudioPlayerMock
import de.stefan.lang.foundationCore.test.deviceinfo.DeviceInfoMock
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
