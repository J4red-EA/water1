package com.example.water1.model

/**
 * Clase para estadísticas de consumo de agua
 */
data class WaterStats(
    val totalLiters: Double,
    val averageDaily: Double,
    val maxDaily: Double,
    val minDaily: Double,
    val daysRecorded: Int,
    val weeklyData: List<DailyConsumption>
)

data class DailyConsumption(
    val dayName: String,
    val date: String,
    val liters: Double
)
