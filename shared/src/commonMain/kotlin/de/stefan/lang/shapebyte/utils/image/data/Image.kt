package de.stefan.lang.shapebyte.utils.image.data

import de.stefan.lang.shapebyte.utils.resources.Resource

interface Image : Resource

// TODO: separate file
class ImageResource(override val id: String) : Image
