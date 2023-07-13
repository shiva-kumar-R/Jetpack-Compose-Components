package com.example.jetpackcomposecomponents.ui.contract

interface ComponentDetailContract {

    sealed class ComponentDetailViewState {
        object LoadingState: ComponentDetailViewState()

        data class SuccessState(
            val url: String
        ) : ComponentDetailViewState()

        object ErrorState: ComponentDetailViewState()
    }

    sealed class ComponentDetailIntention

    sealed class ComponentDetailEvent
}