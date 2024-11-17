package de.stefan.lang.shapebyte.shared.featuretoggles.data.impl

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

class FeatureToggleParser {
    sealed interface Result {
        data class Success(val featureToggles: List<FeatureToggleData>) : Result
        data class Error(val exception: Exception) : Result
    }

    fun parse(json: String): Result {
        try {
            val result: List<FeatureToggleData> = Json.decodeFromString(json)
            return Result.Success(result)
        } catch (e: SerializationException) {
            return Result.Error(e)
        } catch (e: IllegalArgumentException) {
            return Result.Error(e)
        }
    }
}
