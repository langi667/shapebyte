package de.stefan.lang.foundationCore.api.resources

actual class AppResourceProvider actual constructor() {
    var audioMapping: ResourceFileMapping? = null
        private set

    constructor(audioMapping: ResourceFileMapping?) : this() {
        this.audioMapping = audioMapping
    }
}
