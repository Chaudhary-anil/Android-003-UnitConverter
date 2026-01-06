package com.example.unitconverter.presentation.screen.unitConverter

data class UnitConverterState(
    val inputValue: String = "",
    val fromUnit: String = "m",
    val toUnit: String = "cm",
    val result: String = "0",
    val units: List<String> = listOf<String>("mm","cm","m","inch","km","foot","yard","mile")
)
