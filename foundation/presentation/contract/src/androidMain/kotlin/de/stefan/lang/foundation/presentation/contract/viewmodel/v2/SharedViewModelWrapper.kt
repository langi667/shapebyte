package de.stefan.lang.foundation.presentation.contract.viewmodel.v2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

public open class SharedViewModelWrapper<T : SharedViewModel>(
    protected val wrapped: T,
): ViewModel(), SharedViewModel by wrapped {

    override fun onCleared() {
        super.onCleared()
        wrapped.clear()
    }
}