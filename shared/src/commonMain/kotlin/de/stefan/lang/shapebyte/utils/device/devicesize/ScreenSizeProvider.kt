package de.stefan.lang.shapebyte.utils.device.devicesize

interface ScreenSizeProviding {
    val screenSize: Size
}

expect class ScreenSizeProvider() : ScreenSizeProviding {
    override val screenSize: Size
}
