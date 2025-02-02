package de.stefan.lang.core.os

sealed interface OperatingSystem {
    data object Android : OperatingSystem
    data object IOS : OperatingSystem
}
