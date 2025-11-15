package com.example.water1.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.water1.model.WaterStats

/**
 * Pantalla de estadísticas con gráficos de consumo
 */
@Composable
fun StatsScreen(
    weeklyStats: WaterStats?,
    monthlyStats: WaterStats?,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Semanal", "Mensual")
    
    Column(modifier = modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }
        
        when (selectedTab) {
            0 -> StatsContent(weeklyStats, "Últimos 7 días")
            1 -> StatsContent(monthlyStats, "Últimos 30 días")
        }
    }
}

@Composable
private fun StatsContent(stats: WaterStats?, period: String) {
    if (stats == null || stats.daysRecorded == 0) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Default.WaterDrop,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "No hay datos disponibles",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    "Comienza a registrar tu consumo de agua",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                )
            }
        }
        return
    }
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = period,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        
        // Tarjetas de resumen
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    title = "Total",
                    value = "${"%.1f".format(stats.totalLiters)} L",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Promedio",
                    value = "${"%.1f".format(stats.averageDaily)} L",
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    title = "Máximo",
                    value = "${"%.1f".format(stats.maxDaily)} L",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Mínimo",
                    value = "${"%.1f".format(stats.minDaily)} L",
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        // Gráfico de barras
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Consumo Diario",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    BarChart(
                        data = stats.weeklyData,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                }
            }
        }
        
        // Lista detallada
        item {
            Text(
                text = "Detalle por día",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }
        
        items(stats.weeklyData.filter { it.liters > 0 }.sortedByDescending { it.liters }) { day ->
            DayDetailCard(day)
        }
    }
}

@Composable
private fun StatCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun DayDetailCard(day: com.example.water1.model.DailyConsumption) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = day.dayName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = day.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
            Text(
                text = "${"%.1f".format(day.liters)} L",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun BarChart(
    data: List<com.example.water1.model.DailyConsumption>,
    modifier: Modifier = Modifier
) {
    val maxValue = data.maxOfOrNull { it.liters } ?: 1.0
    val barColor = MaterialTheme.colorScheme.primary
    val axisColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
    
    Canvas(modifier = modifier) {
        val barWidth = size.width / (data.size * 2f)
        val chartHeight = size.height - 60f
        val spacing = barWidth * 0.5f
        
        data.forEachIndexed { index, dayData ->
            val barHeight = if (maxValue > 0) (dayData.liters / maxValue * chartHeight).toFloat() else 0f
            val x = index * (barWidth + spacing) + spacing
            
            // Dibujar barra
            drawRect(
                color = barColor,
                topLeft = Offset(x, chartHeight - barHeight),
                size = Size(barWidth, barHeight)
            )
            
            // Dibujar etiqueta del día
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    dayData.dayName,
                    x + barWidth / 2,
                    size.height - 30f,
                    android.graphics.Paint().apply {
                        color = axisColor.hashCode()
                        textSize = 28f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
                
                // Dibujar valor
                if (dayData.liters > 0) {
                    drawText(
                        "%.0f".format(dayData.liters),
                        x + barWidth / 2,
                        chartHeight - barHeight - 10f,
                        android.graphics.Paint().apply {
                            color = barColor.hashCode()
                            textSize = 24f
                            textAlign = android.graphics.Paint.Align.CENTER
                        }
                    )
                }
            }
        }
        
        // Línea base
        drawLine(
            color = axisColor,
            start = Offset(0f, chartHeight),
            end = Offset(size.width, chartHeight),
            strokeWidth = 2f
        )
    }
}
