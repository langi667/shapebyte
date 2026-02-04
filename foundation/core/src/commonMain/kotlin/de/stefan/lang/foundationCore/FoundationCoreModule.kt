package de.stefan.lang.foundationCore

import de.stefan.lang.foundation.core.contract.FoundationCoreContract
import de.stefan.lang.foundation.core.contract.assets.FileAssetLoader
import de.stefan.lang.foundation.core.contract.audio.AudioPlayer
import de.stefan.lang.foundation.core.contract.deviceinfo.DeviceInfoProvider
import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategoryProvider
import de.stefan.lang.foundation.core.contract.devicesize.ScreenSizeProviding
import de.stefan.lang.foundation.core.contract.os.OperatingSystemInfoProviding
import de.stefan.lang.foundation.core.contract.resources.AppResourceProvider
import de.stefan.lang.foundation.core.contract.stringformatter.DateTimeStringFormatter
import de.stefan.lang.foundation.core.fake.assets.FileAssetLoaderFake
import de.stefan.lang.foundation.core.fake.audio.AudioPlayerFake
import de.stefan.lang.foundation.core.fake.deviceinfo.DeviceInfoProviderFake
import de.stefan.lang.foundation.core.implementation.assets.FileAssetLoaderImpl
import de.stefan.lang.foundation.core.implementation.audio.AudioPlayerImpl
import de.stefan.lang.foundation.core.implementation.deviceinfo.DeviceInfoProviderImpl
import de.stefan.lang.foundation.core.implementation.devicesize.DeviceSizeCategoryProviderImpl
import de.stefan.lang.foundation.core.implementation.safearea.SafeAreaDetector
import de.stefan.lang.foundation.core.implementation.stringformatter.DateTimeStringFormatterImpl
import de.stefan.lang.foundationCore.FoundationCoreModule.appResourceProvider
import de.stefan.lang.shapebyte.foundation.core.generated.Dependencies
import de.stefan.lang.shapebyte.foundation.core.generated.Module
import org.koin.core.component.get

public object FoundationCoreModule :
    Module(
        globalBindings = {
            single<OperatingSystemInfoProviding> { get<DeviceInfoProvider>() }
            single<ScreenSizeProviding> { get<DeviceInfoProvider>() }
            single<DeviceSizeCategoryProvider> { DeviceSizeCategoryProviderImpl(screenSizeProvider = get()) }
            single<AppResourceProvider> { appResourceProvider }

            single<SafeAreaDetector> {
                SafeAreaDetector(logger = Dependencies.logger())
            }

            single<DateTimeStringFormatter> { DateTimeStringFormatterImpl() }
        },
        productionBindings = {
            single<FileAssetLoader> {
                FileAssetLoaderImpl(logging = Dependencies.logger(), appContextProvider = get())
            }

            single<DeviceInfoProvider> { DeviceInfoProviderImpl(safeAreaDetector = get()) }

            factory<AudioPlayer> {
                AudioPlayerImpl(
                    appContextProvider = get(),
                    appResourceProvider = get(),
                    logger = Dependencies.logger(),
                )
            }
        },
        testBindings = {
            single<FileAssetLoader> { FileAssetLoaderFake() }
            factory<AudioPlayer> { AudioPlayerFake() }
            single<DeviceInfoProvider> { DeviceInfoProviderFake() }
        },
    ),
    FoundationCoreContract {
    private lateinit var appResourceProvider: AppResourceProvider

    public fun initialize(appResourceProvider: AppResourceProvider) {
        this.appResourceProvider = appResourceProvider
    }

    public override fun dateTimeStringFormatter(): DateTimeStringFormatter = get()
    public override fun fileAssetLoader(): FileAssetLoader = get()
    public override fun audioPlayer(): AudioPlayer = get()
    public override fun deviceInfoProvider(): DeviceInfoProvider = get()
    public override fun deviceSizeCategoryProvider(): DeviceSizeCategoryProvider = get()
}
