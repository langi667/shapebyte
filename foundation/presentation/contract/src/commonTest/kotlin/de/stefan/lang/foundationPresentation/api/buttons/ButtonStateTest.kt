package de.stefan.lang.foundationPresentation.api.buttons

import de.stefan.lang.foundation.presentation.contract.buttons.ButtonState
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ButtonStateTest {
    @Test
    fun `is visible must be true for visible state`() {
        listOf(
            ButtonState.Visible(),
            ButtonState.Hidden,
        ).forEach { buttonState ->
            when (buttonState) {
                is ButtonState.Hidden -> assertFalse(buttonState.isVisible)
                is ButtonState.Visible -> assertTrue(buttonState.isVisible)
            }
        }
    }

    @Test
    fun `on click action must be available for visible state`() {
        var clicked = false
        listOf(
            ButtonState.Visible { clicked = true },
            ButtonState.Hidden,
        ).forEach { buttonState ->
            when (buttonState) {
                is ButtonState.Hidden -> assertNull(buttonState.onClickAction)
                is ButtonState.Visible -> {
                    buttonState.onClickAction?.invoke()
                    assertTrue(clicked)
                }
            }
        }
    }
}
