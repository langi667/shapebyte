package de.stefan.lang.shapebyte.featureToggles.data.implementation

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

public class FeatureToggleParser {
    public sealed interface Result {
        public data class Success(val featureToggles: List<FeatureToggleData>) : Result
        public data class Error(val exception: Exception) : Result
    }

    public fun parse(json: String): Result {
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
