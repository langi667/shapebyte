package de.stefan.lang.shapebyte.android.navigation

import android.os.Bundle
import de.stefan.lang.shapebyte.features.navigation.api.NavigationParams
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class ExtBundleTest {
    @Test
    fun testWorkoutIdReturnsCorrectId() {
        val workoutId = "id12345"
        val sut = Bundle()
        sut.putString(de.stefan.lang.shapebyte.features.navigation.api.NavigationParams.workoutIdParam, workoutId)

        assertEquals(workoutId, sut.workoutId())
    }

    @Test
    fun testWorkoutIdIsNullIfWorkoutNotSet() {
        val sut = Bundle()
        assertNull(sut.workoutId())
    }

    @Test
    fun testWorkoutIdOrShouldReturnCorrectId() {
        val workoutId = "id12345"
        val sut = Bundle()
        sut.putString(de.stefan.lang.shapebyte.features.navigation.api.NavigationParams.workoutIdParam, workoutId)

        assertEquals(workoutId, sut.workoutIdOr("fallback"))
    }

    @Test
    fun testWorkoutIdOrShouldReturnFallbackIfIdNotSet() {
        val fallback = "fallback"
        val sut = Bundle()
        assertEquals(fallback, sut.workoutIdOr(fallback))
    }
}