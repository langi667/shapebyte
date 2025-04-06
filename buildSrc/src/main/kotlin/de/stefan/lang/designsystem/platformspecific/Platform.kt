package de.stefan.lang.designsystem.platformspecific

sealed class Platform {
    sealed interface Mobile

    object Android : Platform(), Mobile
    object iOS : Platform(), Mobile
}