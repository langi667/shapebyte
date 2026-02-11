package de.stefan.lang.foundation.presentation.contract.viewmodel.v2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

public class SharedViewModelFactory(
    private val provider: () -> SharedViewModel,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val wrapped = provider()
        return SharedViewModelWrapper(wrapped) as T
    }
}

public class SharedViewModelFactoryOneArg<ARG>(
    private val provider: (ARG) -> SharedViewModel,
    private val arg: ARG,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val wrapped = provider(arg)
        return SharedViewModelWrapper(wrapped) as T
    }
}
