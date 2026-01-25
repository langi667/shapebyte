package de.stefan.lang.foundation.core.contract.audio

import de.stefan.lang.foundation.core.contract.resources.ResourceFile

data class AudioResource(override val id: String) : Audio, ResourceFile
