package de.stefan.lang.shapebyte

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
