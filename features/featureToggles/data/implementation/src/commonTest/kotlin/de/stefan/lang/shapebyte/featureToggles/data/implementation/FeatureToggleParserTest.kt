package de.stefan.lang.shapebyte.featureToggles.data.implementation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class FeatureToggleParserTest : BaseFeatureToggleDataTest() {
    @Test
    fun `should return raw values from json string`() {
        val json = """
           [
              {
                "identifier": "QUICK_WORKOUTS",
                "state": "ENABLED"
              },
              {
                "identifier": "ADVANCED_METRICS",
                "state": "DISABLED"
              }
        ]
        """

        val sut = FeatureToggleParser()
        val expectedParsed = listOf(
            FeatureToggleData("QUICK_WORKOUTS", "ENABLED"),
            FeatureToggleData("ADVANCED_METRICS", "DISABLED"),
        )

        val expected = FeatureToggleParser.Result.Success(expectedParsed)
        assertEquals(expected, sut.parse(json))
    }

    @Test
    fun `should return empty list from empty json string`() {
        val json = """
           [
            ]
        """

        val sut = FeatureToggleParser()
        val expectedParsed = emptyList<FeatureToggleData>()

        val expected = FeatureToggleParser.Result.Success(expectedParsed)
        assertEquals(expected, sut.parse(json))
    }

    @Test
    fun `should return error on invalid format`() {
        val json = """
           [
              
                "identifier": "QUICK_WORKOUTS",
                "state": "ENABLED"
              }
              {
                "identifier": "ADVANCED_METRICS",
                "state": "DISABLED"
              }
        ]
        """

        val sut = FeatureToggleParser()
        assertIs<FeatureToggleParser.Result.Error>(sut.parse(json))
    }
}
