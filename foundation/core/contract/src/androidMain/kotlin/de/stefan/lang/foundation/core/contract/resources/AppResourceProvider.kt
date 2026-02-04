package de.stefan.lang.foundation.core.contract.resources

public actual class AppResourceProvider public actual constructor() {
    public var audioMapping: ResourceFileMapping? = null
        private set

    public constructor(audioMapping: ResourceFileMapping?) : this() {
        this.audioMapping = audioMapping
    }
}
