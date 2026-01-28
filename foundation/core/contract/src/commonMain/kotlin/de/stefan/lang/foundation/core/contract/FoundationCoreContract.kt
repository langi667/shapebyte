package de.stefan.lang.foundation.core.contract

import de.stefan.lang.foundation.core.contract.assets.FileAssetLoading
import de.stefan.lang.foundation.core.contract.audio.AudioPlaying
import de.stefan.lang.foundation.core.contract.deviceinfo.DeviceInfoProviding
import de.stefan.lang.foundation.core.contract.stringformatter.DateTimeStringFormatter

interface FoundationCoreContract {
    fun dateTimeStringFormatter(): DateTimeStringFormatter
    fun fileAssetLoader(): FileAssetLoading
    fun audioPlayer(): AudioPlaying
    fun deviceInfoProvider(): DeviceInfoProviding
}
