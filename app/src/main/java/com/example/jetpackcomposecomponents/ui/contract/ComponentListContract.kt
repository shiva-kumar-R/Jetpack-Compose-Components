package com.example.jetpackcomposecomponents.ui.contract

import com.example.jetpackcomposecomponents.entity.Component

interface ComponentListContract {

    sealed class ComponentViewState {
        object LoadingState: ComponentViewState()

        data class SuccessState(
            val successData: List<Component>
        ) : ComponentViewState()

        object ErrorState: ComponentViewState()
    }

    sealed class ComponentIntention {
        object UpdateList: ComponentIntention()
    }

    sealed class ComponentEvent {

    }
}