package de.stefan.lang.shapebyte.featureTogglesData.impl

import de.stefan.lang.coreutils.contract.logging.Loggable
import de.stefan.lang.coreutils.contract.logging.Logging
import de.stefan.lang.coroutines.contract.CoroutineContextProviding
import de.stefan.lang.featureToggles.api.FeatureToggle
import de.stefan.lang.foundationCore.api.assets.FileAsset
import de.stefan.lang.foundationCore.api.assets.FileAssetLoading
import de.stefan.lang.foundationCore.api.loadstate.LoadState
import de.stefan.lang.shapebyte.featureTogglesData.FeatureToggleDatasource
import kotlinx.coroutines.withContext

class DefaultFeatureToggleDatasourceImpl(
    override val logger: Logging,
    private val assetLoader: FileAssetLoading,
    coroutineContextProviding: CoroutineContextProviding,
) : FeatureToggleDatasource, Loggable {

    companion object {
        val defaultAssetFile = FileAsset(
            assetName = "featuretoggles/featuretoggle_defaults.json",
            locatedInBundle = true,
        )
    }

    private val defaultFeatureToggles: List<FeatureToggle> by lazy { readDefaultFeatureToggles() }
    private val dispatcher = coroutineContextProviding.iODispatcher()

    override suspend fun fetchFeatureToggle(
        identifier: String,
    ): LoadState.Result<FeatureToggle> = withContext(dispatcher) {
        val featureToggle = defaultFeatureToggles.firstOrNull { it.identifier == identifier }
        val result = if (featureToggle != null) {
            LoadState.Success(featureToggle)
        } else {
            LoadState.Error(FeatureToggleError.NotFound(identifier))
        }

        return@withContext result
    }

    private fun readDefaultFeatureToggles(): List<FeatureToggle> {
        val assetContent = assetLoader.loadFile(defaultAssetFile)
        when (val parseResult = FeatureToggleParser().parse(assetContent)) {
            is FeatureToggleParser.Result.Success -> {
                return FeatureToggleMapper().map(parseResult.featureToggles)
            }

            is FeatureToggleParser.Result.Error -> {
                logE("Error parsing default feature toggles: ${parseResult.exception}")
                return emptyList()
            }
        }
    }
}
