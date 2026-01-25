package de.stefan.lang.foundation.core.contract.resources

// TODO: test
interface ResourceFile : Resource {
    val name: String
        get() = id

    val fileName: String
        get() = name.substringBeforeLast('.')

    val fileEnding: String
        get() = name.substringAfterLast('.')
}
