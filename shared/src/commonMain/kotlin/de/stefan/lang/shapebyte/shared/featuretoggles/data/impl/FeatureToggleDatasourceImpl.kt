package de.stefan.lang.shapebyte.shared.featuretoggles.data.impl

import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggle
import de.stefan.lang.shapebyte.shared.featuretoggles.data.FeatureToggleDatasource
import de.stefan.lang.shapebyte.shared.loading.data.LoadState
import de.stefan.lang.shapebyte.utils.assets.AssetLoading
import de.stefan.lang.shapebyte.utils.assets.FileAsset
import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FeatureToggleDatasourceImpl(
    override val logger: Logging,
    private val assetLoader: AssetLoading,
) : FeatureToggleDatasource, Loggable {

    companion object {
        val defaultAssetFile = FileAsset(
            assetName = "featuretoggles/featuretoggle_defaults.json",
            locatedInBundle = true,
        )
    }

    private val defaultFeatureToggles = MutableSharedFlow<List<FeatureToggle>>(replay = 1)
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        scope.launch {
            val featureToggles = readDefaultFeatureToggles()
            defaultFeatureToggles.emit(featureToggles)
        }
    }

    override fun fetchFeatureToggle(
        identifier: String,
    ): Flow<LoadState<FeatureToggle>> =
        defaultFeatureToggles
            .map { featureToggles ->
                featureToggles.find { it.identifier == identifier }
            }.map { featureToggle ->
                featureToggle?.let {
                    LoadState.Success(it)
                } ?: run {
                    LoadState.Error(FeatureToggleError.NotFound(identifier))
                }
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
