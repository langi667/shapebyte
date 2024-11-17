package de.stefan.lang.shapebyte.viewmodel.ui

import de.stefan.lang.shapebyte.shared.viewmodel.ui.UIState
import de.stefan.lang.shapebyte.utils.BaseTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UIStateTest : BaseTest() {
    @Test
    fun `dataOrNull returns data when UIState is Data`() {
        val data = "data"
        var sut: UIState = UIState.Data(data)
        val result = sut.dataOrNull<String>()
        assertEquals(data, result)

        assertNull(sut.dataOrNull<Int>())

        sut = UIState.Idle
        assertNull(sut.dataOrNull<Any>())

        sut = UIState.Loading
        assertNull(sut.dataOrNull<Any>())
    }
}
