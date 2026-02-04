package de.stefan.lang.foundation.core.contract

import de.stefan.lang.foundation.core.contract.assets.FileAssetLoader
import de.stefan.lang.foundation.core.contract.audio.AudioPlayer
import de.stefan.lang.foundation.core.contract.deviceinfo.DeviceInfoProvider
import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategoryProvider
import de.stefan.lang.foundation.core.contract.stringformatter.DateTimeStringFormatter

public interface FoundationCoreContract {
    public fun dateTimeStringFormatter(): DateTimeStringFormatter
    public fun fileAssetLoader(): FileAssetLoader
    public fun audioPlayer(): AudioPlayer
    public fun deviceInfoProvider(): DeviceInfoProvider
    public fun deviceSizeCategoryProvider(): DeviceSizeCategoryProvider
}
