package de.stefan.lang.foundationCore.audio

import de.stefan.lang.foundationCore.resources.ResourceFile

data class AudioResource(override val id: String) : Audio, ResourceFile
