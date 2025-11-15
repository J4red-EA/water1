package com.example.water1.model

import java.time.LocalDate

/**
 * Clase que representa un registro de consumo de agua
 */
data class WaterConsumption(
    val id: Long = 0,
    val date: LocalDate,
    val liters: Double,
    val activity: String = "", // Ejemplo: ducha, lavado, riego, etc.
    val notes: String = ""
)
