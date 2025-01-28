package de.stefan.lang.shapebyte.utils.audio

import de.stefan.lang.shapebyte.utils.resources.ResourceFile

data class AudioResource(override val id: String) : Audio, ResourceFile
