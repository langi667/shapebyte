package de.stefan.lang.shapebyte.designsystem.data

data class FontCollection(
    val title: FontDescriptor,
    val subtitle: FontDescriptor,

    val h1: FontDescriptor,
    val h2: FontDescriptor,
    val h3: FontDescriptor,
    val h4: FontDescriptor,

    val body: FontDescriptor,
    val footnote: FontDescriptor,
)
