package de.stefan.lang.foundation.core.contract.os

sealed interface OperatingSystem {
    data object Android : OperatingSystem
    data object IOS : OperatingSystem
}
