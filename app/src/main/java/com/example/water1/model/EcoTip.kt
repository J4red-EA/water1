package com.example.water1.model

/**
 * Recomendación ecológica sobre el cuidado del agua
 */
data class EcoTip(
    val title: String,
    val description: String,
    val category: TipCategory
)

enum class TipCategory {
    GENERAL,
    SHOWER,
    WASHING,
    GARDEN,
    KITCHEN,
    ALERT
}
