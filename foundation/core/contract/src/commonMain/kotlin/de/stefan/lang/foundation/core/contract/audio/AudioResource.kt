package de.stefan.lang.foundation.core.contract.audio

import de.stefan.lang.foundation.core.contract.resources.ResourceFile

public data class AudioResource public constructor(override val id: String) : Audio, ResourceFile
