package de.stefan.lang.shapebyte.utils.app.appresources

import de.stefan.lang.shapebyte.utils.resources.ResourceFileMapping

actual class AppResourceProvider actual constructor() {
    var audioMapping: ResourceFileMapping? = null
        private set

    constructor(audioMapping: ResourceFileMapping?): this() {
        this.audioMapping = audioMapping
    }
}
