package com.example.unitconverter.presentation.screen.unitConverter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.String

@HiltViewModel
class UnitConverterViewModel @Inject constructor(): ViewModel() {

    var state by mutableStateOf(UnitConverterState())
        private set

    init {
        convert()
    }

    fun onEvent(event: UnitConverterEvent) {
        when(event) {
            is UnitConverterEvent.FromValueChanged -> {
                state = state.copy(fromUnit = event.fromValue)
                convert()
            }
            is UnitConverterEvent.InputValueChanged -> {
                state = state.copy(inputValue = event.value)
                convert()
            }
            UnitConverterEvent.Reset -> unitConverterReset()
            UnitConverterEvent.Swap -> {
                swapValue()
                convert()
            }
            is UnitConverterEvent.ToValueChanged -> {
                state = state.copy(toUnit = event.toValue)
                convert()
            }
        }
    }

    private fun unitConverterReset() {
        state = state.copy(
            inputValue = "",
            fromUnit = "m",
            toUnit = "cm",
            result = "0"
        )
    }

    private fun convert() {
        val inputValue = state.inputValue.toDoubleOrNull() ?: 0.0
        val fromFactor = factor(state.fromUnit)
        val toFactor = factor(state.toUnit)
        val result = inputValue * fromFactor / toFactor

        state = state.copy(
            result = "%.4f".format(result).trimEnd('0').trimEnd('.')
        )
    }

    private fun factor(unit: String): Double =
        when(unit) {
            "m" -> 1.0
            "cm" -> 0.01
            "mm" -> 0.001
            "inch" -> 0.0254
            "km" -> 1000.0
            "foot" -> 0.3048
            "yard" -> 0.9144
            "mile" -> 1609.34
            else -> 1.0
    }

    private fun swapValue() {
        viewModelScope.launch {
            state = state.copy(
                fromUnit = state.toUnit,
                toUnit = state.fromUnit
            )
        }
    }

}































