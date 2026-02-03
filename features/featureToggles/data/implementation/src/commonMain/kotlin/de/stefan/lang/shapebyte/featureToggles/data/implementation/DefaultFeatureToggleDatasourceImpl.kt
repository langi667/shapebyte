package de.stefan.lang.shapebyte.featureToggles.data.implementation

import de.stefan.lang.coroutines.contract.CoroutineContextProvider
import de.stefan.lang.foundation.core.contract.assets.FileAsset
import de.stefan.lang.foundation.core.contract.assets.FileAssetLoader
import de.stefan.lang.foundation.core.contract.loadstate.LoadState
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggle
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleDatasource
import de.stefan.lang.shapebyte.featureToggles.data.contract.FeatureToggleError
import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.Logger
import kotlinx.coroutines.withContext

class DefaultFeatureToggleDatasourceImpl(
    override val logger: Logger,
    private val assetLoader: FileAssetLoader,
    coroutineContextProviding: CoroutineContextProvider,
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
