package de.stefan.lang.foundation.presentation.contract.buttons

public sealed class ButtonState {
    public data class Visible(val onClick: () -> Unit = {}) : ButtonState()
    public data object Hidden : ButtonState()

    public val isVisible: Boolean get() = (this as? Visible) != null
    public val onClickAction: (() -> Unit)?
        get() = when (this) {
            is Visible -> onClick
            is Hidden -> null
        }
}
