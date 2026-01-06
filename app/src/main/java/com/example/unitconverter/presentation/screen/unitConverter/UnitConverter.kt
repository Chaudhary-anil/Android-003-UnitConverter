package com.example.unitconverter.presentation.screen.unitConverter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.unitconverter.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConverterUi(viewModel: UnitConverterViewModel = hiltViewModel()) {

    val state = viewModel.state
    var fromExpanded by remember { mutableStateOf(false) }
    var toExposed by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.navigationBarsPadding(),  //fab lai bottom navigation ma overlap na huna dina lai
                onClick = { viewModel.onEvent(event = UnitConverterEvent.Reset) }
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                Text(stringResource(R.string.refresh))
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.app_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = MaterialTheme.colorScheme.surfaceDim)
                    .padding(16.dp)
            ) {
                Text(stringResource(R.string.value))
                InputValueField(value = state.inputValue) {
                    viewModel.onEvent(
                        event = UnitConverterEvent.InputValueChanged(
                            it
                        )
                    )
                }
            }

            Text(stringResource(R.string.from))
            ExposedDropdownMenuBox(
                expanded = fromExpanded,
                onExpandedChange = { fromExpanded = it }
            ) {
                OutlinedTextField(
                    value = state.fromUnit,
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
//                    trailingIcon = { Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Drop down menu")},
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = fromExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = fromExpanded,
                    onDismissRequest = { fromExpanded = false }
                ) {
                    state.units.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(stringResource(getUnitString(unit = unit))) },
                            onClick = {
                                viewModel.onEvent(
                                    event = UnitConverterEvent.FromValueChanged(
                                        fromValue = unit
                                    )
                                )
                                fromExpanded = false
                            }
                        )
                    }
                }
            }

            IconButton(
                onClick = { viewModel.onEvent(UnitConverterEvent.Swap) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(Icons.Default.SwapVert, contentDescription = "swap")
            }

            Text(text = stringResource(R.string.to))
            ExposedDropdownMenuBox(
                expanded = toExposed,
                onExpandedChange = { toExposed = it }
            ) {
                OutlinedTextField(
                    value = state.toUnit,
                    onValueChange = { },
                    readOnly = true,
                    singleLine = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Drop down menu"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = toExposed,
                    onDismissRequest = { toExposed = false }
                ) {
                    state.units.forEach { Unit ->
                        DropdownMenuItem(
                            text = { Text(text = stringResource(getUnitString(unit = Unit))) },
                            onClick = {
                                viewModel.onEvent(event = UnitConverterEvent.ToValueChanged(Unit))
                                toExposed = false
                            }
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    stringResource(R.string.result, state.result),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

        }
    }
}

@Composable
fun getUnitString(unit: String): Int = when (unit) {
    "mm" -> R.string.mm
    "cm" -> R.string.cm
    "m" -> R.string.m
    "km" -> R.string.km
    "inch" -> R.string.inch
    "foot" -> R.string.foot
    "yard" -> R.string.yard
    "mile" -> R.string.mile
    else -> R.string.m
}

































