package de.stefan.lang.foundation.core.contract

import de.stefan.lang.foundation.core.contract.assets.FileAssetLoader
import de.stefan.lang.foundation.core.contract.audio.AudioPlayer
import de.stefan.lang.foundation.core.contract.deviceinfo.DeviceInfoProvider
import de.stefan.lang.foundation.core.contract.devicesize.DeviceSizeCategoryProvider
import de.stefan.lang.foundation.core.contract.stringformatter.DateTimeStringFormatter

interface FoundationCoreContract {
    fun dateTimeStringFormatter(): DateTimeStringFormatter
    fun fileAssetLoader(): FileAssetLoader
    fun audioPlayer(): AudioPlayer
    fun deviceInfoProvider(): DeviceInfoProvider
    fun deviceSizeCategoryProvider(): DeviceSizeCategoryProvider
}
