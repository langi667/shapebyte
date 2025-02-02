package de.stefan.lang.core.audio

import de.stefan.lang.core.resources.ResourceFile

data class AudioResource(override val id: String) : Audio, ResourceFile
