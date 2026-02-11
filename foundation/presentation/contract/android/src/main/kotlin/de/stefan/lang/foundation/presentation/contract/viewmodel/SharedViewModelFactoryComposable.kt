package de.stefan.lang.foundation.presentation.contract.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
public fun <T : SharedViewModel> sharedViewModel(
    provider: () -> T,
): SharedViewModelWrapper<T> {
    val factory = SharedViewModelFactory(provider)
    val viewModel: SharedViewModelWrapper<T> = viewModel(factory = factory)

    return viewModel
}
