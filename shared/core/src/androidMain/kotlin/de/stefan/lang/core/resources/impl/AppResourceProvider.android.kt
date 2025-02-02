package de.stefan.lang.core.resources.impl

import de.stefan.lang.core.resources.ResourceFileMapping

actual class AppResourceProvider actual constructor() {
    var audioMapping: ResourceFileMapping? = null
        private set

    constructor(audioMapping: ResourceFileMapping?) : this() {
        this.audioMapping = audioMapping
    }
}
