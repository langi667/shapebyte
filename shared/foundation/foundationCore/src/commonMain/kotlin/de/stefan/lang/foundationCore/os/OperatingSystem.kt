package de.stefan.lang.foundationCore.os

sealed interface OperatingSystem {
    data object Android : OperatingSystem
    data object IOS : OperatingSystem
}
