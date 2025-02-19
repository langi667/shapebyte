package de.stefan.lang.shapebyte.featureToggles.data.impl

import de.stefan.lang.shapebyte.featureToggles.BaseFeatureToggleTest
import de.stefan.lang.shapebyte.featureToggles.data.FeatureToggle
import de.stefan.lang.shapebyte.featureToggles.data.FeatureToggleState
import kotlin.test.Test
import kotlin.test.assertEquals

class FeatureToggleMapperTest : BaseFeatureToggleTest() {
    @Test
    fun `map raw feature toggle data`() {
        val id = "identifier"
        var data = FeatureToggleData(
            identifier = id,
            state = "ENABLED",
        )

        val sut = FeatureToggleMapper()
        var expected = FeatureToggle(id, FeatureToggleState.ENABLED)
        assertEquals(expected, sut.map(data))

        data = FeatureToggleData(
            identifier = id,
            state = "DISABLED",
        )

        expected = FeatureToggle(id, FeatureToggleState.DISABLED)
        assertEquals(expected, sut.map(data))
    }

    @Test
    fun `state should be disabled if state string is invalid`() {
        val id = "identifier"
        val data = FeatureToggleData(
            identifier = id,
            state = "INVALID STATE",
        )

        val sut = FeatureToggleMapper()
        val expected = FeatureToggle(id, FeatureToggleState.DISABLED)
        assertEquals(expected, sut.map(data))
    }

    @Test
    fun `should map list correctly`() {
        val id = "identifier"

        val list = listOf(
            FeatureToggleData(
                identifier = "${id}1",
                state = "ENABLED",
            ),
            FeatureToggleData(
                identifier = "${id}2",
                state = "DISABLED",
            ),
        )

        val sut = FeatureToggleMapper()
        val expected = listOf(
            FeatureToggle("${id}1", FeatureToggleState.ENABLED),
            FeatureToggle("${id}2", FeatureToggleState.DISABLED),
        )
        assertEquals(expected, sut.map(list))
    }
}
