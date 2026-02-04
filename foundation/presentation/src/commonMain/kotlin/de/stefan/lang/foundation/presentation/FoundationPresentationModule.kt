package de.stefan.lang.foundation.presentation

import de.stefan.lang.foundation.presentation.contract.FoundationPresentationContract
import de.stefan.lang.foundation.presentation.contract.dimension.DimensionProvider
import de.stefan.lang.shapebyte.foundation.presentation.generated.Dependencies
import de.stefan.lang.shapebyte.foundation.presentation.generated.Module
import org.koin.core.component.get

public object FoundationPresentationModule :
    Module(
        globalBindings = {
            single<DimensionProvider> {
                DimensionProvider(
                    deviceSizeCategoryProvider = Dependencies.deviceSizeCategoryProvider(),
                )
            }
        },
    ),
    FoundationPresentationContract {

    public override fun dimensionProvider(): DimensionProvider = get()
}
