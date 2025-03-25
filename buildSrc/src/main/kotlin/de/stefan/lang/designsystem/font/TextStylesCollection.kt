package de.stefan.lang.designsystem.font

interface TextStylesCollection : TextStyles {
    val displayLarge: TextStyle
    val displayMedium: TextStyle
    val displaySmall: TextStyle

    val headlineLarge: TextStyle
    val headlineMedium: TextStyle
    val headlineSmall: TextStyle

    val titleLarge: TextStyle
    val titleMedium: TextStyle
    val titleSmall: TextStyle

    val bodyLarge: TextStyle
    val bodyMedium: TextStyle
    val bodySmall: TextStyle

    val labelLarge: TextStyle
    val labelMedium: TextStyle
    val labelSmall: TextStyle

    val all: Map<String, TextStyle>
}