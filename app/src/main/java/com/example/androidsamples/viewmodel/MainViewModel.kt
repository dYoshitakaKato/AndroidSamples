package com.example.androidsamples.viewmodel

import androidx.lifecycle.ViewModel
import com.example.androidsamples.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    val uiState: StateFlow<UiState> = MutableStateFlow(
        UiState(
            listOf(Screen.RichEditText)
        )
    )

    data class UiState(val screens: List<Screen>)
}