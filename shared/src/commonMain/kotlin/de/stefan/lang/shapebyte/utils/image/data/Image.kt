package de.stefan.lang.shapebyte.utils.image.data

interface Image {
    val id: String
}

// TODO: separate file
class ImageResource(override val id: String) : Image
