package de.stefan.lang.designsystem
import de.stefan.lang.designsystem.animation.AnimationDurations
import de.stefan.lang.designsystem.color.ColorScheme
import de.stefan.lang.designsystem.dimension.Dimensions
import de.stefan.lang.designsystem.font.TextStyles
import de.stefan.lang.designsystem.platformspecific.Platform
import de.stefan.lang.designsystem.shapes.Shapes
import de.stefan.lang.designsystem.spacing.Spacings

data class ThemeDataProvider(
    private val platform: Platform,
    private val data: ThemeData
): ThemeContent  {
    override val textStyles: TextStyles
        get() {
            return when (platform) {
                Platform.Android -> data.android.textStyles ?: data.textStyles
                Platform.iOS -> data.iOS.textStyles ?: data.textStyles
            }
        }

    override val animationDurations: AnimationDurations
        get() {
            return when (platform) {
                Platform.Android -> data.android.animationDurations ?: data.animationDurations
                Platform.iOS -> data.iOS.animationDurations ?: data.animationDurations
            }
        }

    override val lightColorScheme: ColorScheme
        get() {
            return when (platform) {
                Platform.Android -> data.android.lightColorScheme ?: data.lightColorScheme
                Platform.iOS -> data.iOS.lightColorScheme ?: data.lightColorScheme
            }
        }

    override val darkColorScheme: ColorScheme
        get() {
            return when (platform) {
                Platform.Android -> data.android.darkColorScheme ?: data.darkColorScheme
                Platform.iOS -> data.iOS.darkColorScheme ?: data.darkColorScheme
            }
        }

    override val shapes: Shapes
        get() {
            return when (platform) {
                Platform.Android -> data.android.shapes ?: data.shapes
                Platform.iOS -> data.iOS.shapes ?: data.shapes
            }
        }

    override val dimensions: Dimensions
        get() {
            return when (platform) {
                Platform.Android -> data.android.dimensions ?: data.dimensions
                Platform.iOS -> data.iOS.dimensions ?: data.dimensions
            }
        }

    override val spacings: Spacings
        get() {
            return when (platform) {
                Platform.Android -> data.android.spacings ?: data.spacings
                Platform.iOS -> data.iOS.spacings ?: data.spacings
            }
        }
}