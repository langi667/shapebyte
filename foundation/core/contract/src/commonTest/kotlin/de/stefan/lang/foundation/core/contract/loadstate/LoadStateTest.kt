package de.stefan.lang.foundation.core.contract.loadstate

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class LoadStateTest {
    @Test
    fun `isLoading should return correct value`() {
        assertTrue(LoadState.Loading.isLoading)
        assertFalse(LoadState.Success("Data").isLoading)
        assertFalse(LoadState.Error(Exception()).isLoading)
    }

    @Test
    fun `dataOrNull should return data`() {
        val data = "Data"
        val dataState = LoadState.Success(data)

        assertEquals(data, dataState.dataOrNull())
        assertNull(LoadState.Loading.dataOrNull<Any>())
        assertNull(LoadState.Error(Exception()).dataOrNull<Any>())
    }

    @Test
    fun `errorOrNull should return error`() {
        val error = Exception("Exception Test")
        assertEquals(error, LoadState.Error(error).errorOrNull())
        assertNull(LoadState.Loading.errorOrNull())
        assertNull(LoadState.Success("Data").errorOrNull())
    }
}
