package de.stefan.lang.foundation.core.contract.resources

// TODO: test
public interface ResourceFile : Resource {
    public val name: String
        get() = id

    public val fileName: String
        get() = name.substringBeforeLast('.')

    public val fileEnding: String
        get() = name.substringAfterLast('.')
}
