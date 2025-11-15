package com.example.water1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.water1.model.EcoTip
import com.example.water1.model.TipCategory
import com.example.water1.utils.EcoTipsProvider

/**
 * Pantalla de recomendaciones ecológicas
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipsScreen(modifier: Modifier = Modifier) {
    val allTips = EcoTipsProvider.getAllTips()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recomendaciones Ecológicas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.WaterDrop,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Cada gota cuenta",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "Pequeñas acciones generan grandes cambios",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
            
            items(allTips) { tip ->
                TipCard(tip)
            }
        }
    }
}

@Composable
private fun TipCard(tip: EcoTip) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = getCategoryIcon(tip.category),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = getCategoryColor(tip.category)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = tip.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = tip.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = getCategoryColor(tip.category).copy(alpha = 0.1f),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = getCategoryName(tip.category),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = getCategoryColor(tip.category)
                    )
                }
            }
        }
    }
}

@Composable
private fun getCategoryIcon(category: TipCategory) = when (category) {
    TipCategory.SHOWER -> Icons.Default.Shower
    TipCategory.WASHING -> Icons.Default.LocalLaundryService
    TipCategory.GARDEN -> Icons.Default.Grass
    TipCategory.KITCHEN -> Icons.Default.Kitchen
    TipCategory.ALERT -> Icons.Default.Warning
    else -> Icons.Default.Eco
}

@Composable
private fun getCategoryColor(category: TipCategory) = when (category) {
    TipCategory.SHOWER -> MaterialTheme.colorScheme.primary
    TipCategory.WASHING -> MaterialTheme.colorScheme.secondary
    TipCategory.GARDEN -> MaterialTheme.colorScheme.tertiary
    TipCategory.KITCHEN -> MaterialTheme.colorScheme.primary
    TipCategory.ALERT -> MaterialTheme.colorScheme.error
    else -> MaterialTheme.colorScheme.primary
}

private fun getCategoryName(category: TipCategory) = when (category) {
    TipCategory.SHOWER -> "Ducha"
    TipCategory.WASHING -> "Lavado"
    TipCategory.GARDEN -> "Jardín"
    TipCategory.KITCHEN -> "Cocina"
    TipCategory.ALERT -> "Alerta"
    else -> "General"
}
