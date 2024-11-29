package de.stefan.lang.shapebyte.android.buttons.ui

import de.stefan.lang.shapebyte.utils.buttons.ButtonState
import junit.framework.TestCase.assertTrue
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNull

class ButtonStateTest {
    @Test
    fun testIsVisible() {
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
    fun `Your on click`() {
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
