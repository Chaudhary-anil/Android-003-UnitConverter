package com.example.unitconverter.presentation.screen.unitConverter

sealed interface UnitConverterEvent {
    data class InputValueChanged(val value: String): UnitConverterEvent
    data class FromValueChanged(val fromValue: String): UnitConverterEvent
    data class ToValueChanged(val toValue: String): UnitConverterEvent
    object Reset: UnitConverterEvent
    object Swap: UnitConverterEvent
}