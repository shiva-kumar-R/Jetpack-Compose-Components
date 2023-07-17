package com.example.jetpackcomposecomponents.ui.contract

interface UpdateComponentsContract {

    sealed class UpdateComponentsViewState {
        object LoadingState: UpdateComponentsViewState()

        data class SuccessState(
            val data: String
        ) : UpdateComponentsViewState()

        object ErrorState: UpdateComponentsViewState()
    }

    sealed class UpdateComponentsIntention {
        data class UpdateData(val newData: String): UpdateComponentsIntention()
        object SaveData: UpdateComponentsIntention()
    }

    sealed class UpdateComponentsEvent
}