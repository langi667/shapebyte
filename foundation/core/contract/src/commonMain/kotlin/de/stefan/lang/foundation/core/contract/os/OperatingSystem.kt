package de.stefan.lang.foundation.core.contract.os

public sealed interface OperatingSystem {
    public data object Android : OperatingSystem
    public data object IOS : OperatingSystem
}
