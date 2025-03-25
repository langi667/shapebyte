package de.stefan.lang.designsystem.color.ios

import de.stefan.lang.designsystem.color.Color
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AssetColorSetSerializer {
    fun serialize(assetColorSet: AssetColorSet): String {
        val json = Json { prettyPrint = true } // prettyPrint for formatted JSON
        val serialized = json.encodeToString(assetColorSet)

        return serialized
    }
}