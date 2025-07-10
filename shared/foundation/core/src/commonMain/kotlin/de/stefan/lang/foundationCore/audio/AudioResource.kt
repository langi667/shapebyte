package de.stefan.lang.foundationCore.audio

import de.stefan.lang.foundationCore.api.resources.ResourceFile

data class AudioResource(override val id: String) : Audio, ResourceFile
