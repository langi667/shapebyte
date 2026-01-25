package de.stefan.lang.foundation.core.contract.resources

actual class AppResourceProvider actual constructor() {
    var audioMapping: ResourceFileMapping? = null
        private set

    constructor(audioMapping: ResourceFileMapping?) : this() {
        this.audioMapping = audioMapping
    }
}
