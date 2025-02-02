package de.stefan.lang.foundation.ui.buttons

sealed class ButtonState {
    data class Visible(val onClick: () -> Unit = {}) : ButtonState()
    data object Hidden : ButtonState()

    val isVisible: Boolean get() = (this as? Visible) != null
    val onClickAction: (() -> Unit)?
        get() = when (this) {
            is Visible -> onClick
            is Hidden -> null
        }
}
