package com.example.water1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.water1.model.WaterConsumption
import com.example.water1.utils.EcoTipsProvider
import java.time.format.DateTimeFormatter

/**
 * Pantalla principal de inicio
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    consumptions: List<WaterConsumption>,
    todayConsumption: Double,
    threshold: Double,
    showAlert: Boolean,
    onAddClick: () -> Unit,
    onDeleteConsumption: (Long) -> Unit,
    onUpdateThreshold: (Double) -> Unit,
    onDismissAlert: () -> Unit,
    modifier: Modifier = Modifier
) {
    val todayConsumptions = consumptions.filter { 
        it.date == java.time.LocalDate.now() 
    }.sortedByDescending { it.id }
    
    var showThresholdDialog by remember { mutableStateOf(false) }
    
    // Alerta de consumo excedido
    if (showAlert) {
        AlertDialog(
            onDismissRequest = onDismissAlert,
            icon = { Icon(Icons.Default.Warning, contentDescription = null) },
            title = { Text("¡Consumo Elevado!") },
            text = { 
                Text("Has superado el umbral de ${"%.1f".format(threshold)} litros hoy con ${"%.1f".format(todayConsumption)} litros.")
            },
            confirmButton = {
                TextButton(onClick = onDismissAlert) {
                    Text("Entendido")
                }
            }
        )
    }
    
    // Diálogo de configuración de umbral
    if (showThresholdDialog) {
        ThresholdDialog(
            currentThreshold = threshold,
            onDismiss = { showThresholdDialog = false },
            onConfirm = { newThreshold ->
                onUpdateThreshold(newThreshold)
                showThresholdDialog = false
            }
        )
    }
    
    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Cuidado del Agua") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            ),
            actions = {
                IconButton(onClick = { showThresholdDialog = true }) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Configuración",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        )
        
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Resumen del día
            item {
                DailySummaryCard(
                    todayConsumption = todayConsumption,
                    threshold = threshold
                )
            }
            
            // Recomendación del día
            item {
                val tip = if (todayConsumption > 0) {
                    EcoTipsProvider.getTipForConsumption(todayConsumption)
                } else {
                    EcoTipsProvider.getRandomTip()
                }
                EcoTipCard(tip.title, tip.description)
            }
            
            // Registros de hoy
            item {
                Text(
                    text = "Registros de Hoy",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            
            if (todayConsumptions.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No hay registros hoy\nToca + para agregar uno",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                items(todayConsumptions, key = { it.id }) { consumption ->
                    ConsumptionCard(
                        consumption = consumption,
                        onDelete = { onDeleteConsumption(consumption.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun DailySummaryCard(todayConsumption: Double, threshold: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (todayConsumption > threshold) {
                MaterialTheme.colorScheme.errorContainer
            } else {
                MaterialTheme.colorScheme.primaryContainer
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.WaterDrop,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = if (todayConsumption > threshold) {
                    MaterialTheme.colorScheme.onErrorContainer
                } else {
                    MaterialTheme.colorScheme.onPrimaryContainer
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Consumo de Hoy",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "${"%.1f".format(todayConsumption)} litros",
                style = MaterialTheme.typography.displaySmall,
                color = if (todayConsumption > threshold) {
                    MaterialTheme.colorScheme.onErrorContainer
                } else {
                    MaterialTheme.colorScheme.primary
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { (todayConsumption / threshold).toFloat().coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Umbral: ${"%.1f".format(threshold)} L",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun EcoTipCard(title: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                Icons.Default.Lightbulb,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConsumptionCard(
    consumption: WaterConsumption,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar registro") },
            text = { Text("¿Estás seguro de que deseas eliminar este registro?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete()
                        showDeleteDialog = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = consumption.activity.ifEmpty { "Consumo general" },
                    style = MaterialTheme.typography.titleMedium
                )
                if (consumption.notes.isNotEmpty()) {
                    Text(
                        text = consumption.notes,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "${"%.1f".format(consumption.liters)} L",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
private fun ThresholdDialog(
    currentThreshold: Double,
    onDismiss: () -> Unit,
    onConfirm: (Double) -> Unit
) {
    var thresholdText by remember { mutableStateOf(currentThreshold.toString()) }
    var showError by remember { mutableStateOf(false) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Configurar Umbral") },
        text = {
            Column {
                Text("Establece el límite diario de consumo de agua en litros.")
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = thresholdText,
                    onValueChange = { 
                        thresholdText = it
                        showError = false
                    },
                    label = { Text("Litros") },
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    isError = showError,
                    supportingText = {
                        if (showError) {
                            Text("Valor inválido")
                        }
                    },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val value = thresholdText.toDoubleOrNull()
                    if (value != null && value > 0) {
                        onConfirm(value)
                    } else {
                        showError = true
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
