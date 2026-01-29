package de.stefan.lang.foundationCore

import de.stefan.lang.core.di.RootModule
import de.stefan.lang.coroutines.CoreCoroutinesModule
import de.stefan.lang.foundation.core.contract.FoundationCoreContract
import de.stefan.lang.foundation.core.contract.assets.FileAssetLoading
import de.stefan.lang.foundation.core.contract.audio.AudioPlaying
import de.stefan.lang.foundation.core.contract.deviceinfo.DeviceInfoProviding
import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategoryProviding
import de.stefan.lang.foundation.core.contract.devicesize.ScreenSizeProviding
import de.stefan.lang.foundation.core.contract.os.OperatingSystemInfoProviding
import de.stefan.lang.foundation.core.contract.resources.AppResourceProvider
import de.stefan.lang.foundation.core.contract.stringformatter.DateTimeStringFormatter
import de.stefan.lang.foundation.core.fake.assets.FakeFileAssetLoader
import de.stefan.lang.foundation.core.fake.audio.FakeAudioPlayer
import de.stefan.lang.foundation.core.fake.deviceinfo.FakeDeviceInfo
import de.stefan.lang.foundation.core.implementation.assets.FileAssetLoader
import de.stefan.lang.foundation.core.implementation.audio.AudioPlayer
import de.stefan.lang.foundation.core.implementation.deviceinfo.DeviceInfo
import de.stefan.lang.foundation.core.implementation.devicesize.DeviceSizeCategoryProvider
import de.stefan.lang.foundation.core.implementation.safearea.SafeAreaDetector
import de.stefan.lang.foundationCore.FoundationCoreModule.appResourceProvider
import de.stefan.lang.utils.logging.LoggingModule
import org.koin.core.component.get

object FoundationCoreModule :
    RootModule(
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
            single<FileAssetLoading> { FakeFileAssetLoader() }
            factory<AudioPlaying> { FakeAudioPlayer() }
            single<DeviceInfoProviding> { FakeDeviceInfo() }
        },
        dependencies = listOf(
            CoreCoroutinesModule,
            LoggingModule,
        ),
    ),
    FoundationCoreContract {
    private lateinit var appResourceProvider: AppResourceProvider

    fun initialize(appResourceProvider: AppResourceProvider) {
        this.appResourceProvider = appResourceProvider
    }

    override fun dateTimeStringFormatter(): DateTimeStringFormatter = get()
    override fun fileAssetLoader(): FileAssetLoading = get()
    override fun audioPlayer(): AudioPlaying = get()
    override fun deviceInfoProvider(): DeviceInfoProviding = get()
}
