package de.stefan.lang.foundationCore.api.os

sealed interface OperatingSystem {
    data object Android : OperatingSystem
    data object IOS : OperatingSystem
}
