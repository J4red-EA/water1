package com.example.water1.utils

import com.example.water1.model.EcoTip
import com.example.water1.model.TipCategory

/**
 * Proveedor de recomendaciones ecológicas sobre el cuidado del agua
 */
object EcoTipsProvider {
    
    private val tips = listOf(
        EcoTip(
            "Cierra el grifo",
            "Cierra el grifo mientras te cepillas los dientes. Ahorrarás hasta 12 litros por minuto.",
            TipCategory.GENERAL
        ),
        EcoTip(
            "Duchas cortas",
            "Reduce el tiempo de ducha a 5 minutos. Una ducha de 10 minutos consume aproximadamente 200 litros.",
            TipCategory.SHOWER
        ),
        EcoTip(
            "Reutiliza el agua",
            "Reutiliza el agua de lavar verduras para regar las plantas.",
            TipCategory.KITCHEN
        ),
        EcoTip(
            "Repara fugas",
            "Una fuga de una gota por segundo puede desperdiciar más de 30 litros al día.",
            TipCategory.GENERAL
        ),
        EcoTip(
            "Lavadora llena",
            "Usa la lavadora solo cuando esté llena. Ahorrarás hasta 80 litros por carga.",
            TipCategory.WASHING
        ),
        EcoTip(
            "Riega al atardecer",
            "Riega las plantas al atardecer o amanecer para evitar evaporación excesiva.",
            TipCategory.GARDEN
        ),
        EcoTip(
            "Instala reductores",
            "Instala reductores de caudal en grifos y duchas para reducir el consumo hasta un 50%.",
            TipCategory.GENERAL
        ),
        EcoTip(
            "Lava frutas en un recipiente",
            "Lava frutas y verduras en un recipiente en lugar de bajo el grifo abierto.",
            TipCategory.KITCHEN
        ),
        EcoTip(
            "Usa el lavavajillas eficientemente",
            "El lavavajillas consume menos agua que lavar a mano, pero úsalo solo cuando esté lleno.",
            TipCategory.KITCHEN
        ),
        EcoTip(
            "Recoge agua de lluvia",
            "Instala un sistema de recolección de agua de lluvia para regar el jardín.",
            TipCategory.GARDEN
        )
    )
    
    /**
     * Obtiene todas las recomendaciones disponibles
     */
    fun getAllTips(): List<EcoTip> = tips
    
    /**
     * Obtiene una recomendación aleatoria
     */
    fun getRandomTip(): EcoTip = tips.random()
    
    /**
     * Obtiene recomendaciones por categoría
     */
    fun getTipsByCategory(category: TipCategory): List<EcoTip> {
        return tips.filter { it.category == category }
    }
    
    /**
     * Obtiene una recomendación específica según el consumo
     */
    fun getTipForConsumption(dailyLiters: Double): EcoTip {
        return when {
            dailyLiters > 200 -> EcoTip(
                "¡Consumo muy alto!",
                "Tu consumo de ${"%.1f".format(dailyLiters)} litros es elevado. " +
                        "Intenta reducir el tiempo de ducha y cerrar grifos cuando no los uses.",
                TipCategory.ALERT
            )
            dailyLiters > 150 -> EcoTip(
                "Consumo por encima del promedio",
                "Con ${"%.1f".format(dailyLiters)} litros estás por encima del consumo recomendado. " +
                        "Pequeños cambios pueden generar grandes ahorros.",
                TipCategory.ALERT
            )
            dailyLiters > 100 -> EcoTip(
                "Buen trabajo",
                "Tu consumo de ${"%.1f".format(dailyLiters)} litros está en un rango aceptable. " +
                        "¡Sigue así!",
                TipCategory.GENERAL
            )
            else -> EcoTip(
                "¡Excelente!",
                "Tu consumo de ${"%.1f".format(dailyLiters)} litros es muy eficiente. " +
                        "Eres un ejemplo de uso responsable del agua.",
                TipCategory.GENERAL
            )
        }
    }
}
