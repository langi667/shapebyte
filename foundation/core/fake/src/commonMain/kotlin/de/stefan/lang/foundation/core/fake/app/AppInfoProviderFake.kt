package de.stefan.lang.foundation.core.fake.app

import de.stefan.lang.foundation.core.contract.app.AppInfo
import de.stefan.lang.foundation.core.contract.app.AppInfoProvider

public class AppInfoProviderFake : AppInfoProvider {
    override fun appInfo(): AppInfo = AppInfo(
        packageName = "de.stefan.lang.fakeapp",
        versionName = "1.0.0-fake",
        versionCode = 1,
        debugMode = false,
    )
}
