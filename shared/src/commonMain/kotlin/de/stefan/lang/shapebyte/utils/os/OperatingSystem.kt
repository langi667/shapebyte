package de.stefan.lang.shapebyte.utils.os

sealed interface OperatingSystem {
    data object Android : OperatingSystem
    data object IOS : OperatingSystem
}
