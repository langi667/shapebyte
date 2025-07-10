package de.stefan.lang.foundationCore.resources.impl

import de.stefan.lang.foundationCore.api.resources.ResourceFileMapping

actual class AppResourceProvider actual constructor() {
    var audioMapping: ResourceFileMapping? = null
        private set

    constructor(audioMapping: ResourceFileMapping?) : this() {
        this.audioMapping = audioMapping
    }
}
