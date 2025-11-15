package com.example.water1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Pantalla de registro de consumo diario de agua
 */
@Composable
fun AddConsumptionScreen(
    onSave: (Double, String, String) -> Unit,
    onBack: () -> Unit
) {
    var liters by remember { mutableStateOf("") }
    var activity by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var selectedActivity by remember { mutableStateOf("Otro") }
    var expanded by remember { mutableStateOf(false) }
    
    val activities = listOf("Ducha", "Lavado de ropa", "Lavado de platos", "Riego", "Limpieza", "Cocina", "Otro")
    
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Registrar Consumo") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    val litersValue = liters.toDoubleOrNull()
                    if (litersValue != null && litersValue > 0) {
                        onSave(litersValue, selectedActivity, notes)
                        onBack()
                    } else {
                        showError = true
                    }
                },
                icon = { Icon(Icons.Default.Check, contentDescription = "Guardar") },
                text = { Text("Guardar") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Fecha actual
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = "Fecha: ${LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            
            // Campo de litros
            OutlinedTextField(
                value = liters,
                onValueChange = { 
                    liters = it
                    showError = false
                },
                label = { Text("Litros consumidos") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                isError = showError,
                supportingText = {
                    if (showError) {
                        Text("Por favor, ingresa una cantidad válida")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Selector de actividad
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedActivity,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Actividad") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    activities.forEach { activityOption ->
                        DropdownMenuItem(
                            text = { Text(activityOption) },
                            onClick = {
                                selectedActivity = activityOption
                                activity = activityOption
                                expanded = false
                            }
                        )
                    }
                }
            }
            
            // Campo de notas
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notas (opcional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 4
            )
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
