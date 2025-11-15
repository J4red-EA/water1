package com.example.water1.data

import android.content.Context
import android.content.SharedPreferences
import com.example.water1.model.WaterConsumption
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate

/**
 * Repositorio para gestionar la persistencia de datos de consumo de agua
 */
class WaterRepository(context: Context) {
    private val prefs: SharedPreferences = 
        context.getSharedPreferences("water_data", Context.MODE_PRIVATE)
    private val gson = Gson()
    
    companion object {
        private const val KEY_CONSUMPTIONS = "consumptions"
        private const val KEY_THRESHOLD = "threshold"
        private const val DEFAULT_THRESHOLD = 150.0 // Litros por día
        private const val KEY_NEXT_ID = "next_id"
    }
    
    /**
     * Guarda un nuevo registro de consumo
     */
    fun saveConsumption(consumption: WaterConsumption): WaterConsumption {
        val consumptions = getAllConsumptions().toMutableList()
        val newId = getNextId()
        val newConsumption = consumption.copy(id = newId)
        
        consumptions.add(newConsumption)
        saveAllConsumptions(consumptions)
        incrementNextId()
        
        return newConsumption
    }
    
    /**
     * Obtiene todos los registros de consumo
     */
    fun getAllConsumptions(): List<WaterConsumption> {
        val json = prefs.getString(KEY_CONSUMPTIONS, null) ?: return emptyList()
        val type = object : TypeToken<List<WaterConsumptionDto>>() {}.type
        val dtos: List<WaterConsumptionDto> = gson.fromJson(json, type)
        return dtos.map { it.toWaterConsumption() }
    }
    
    /**
     * Obtiene consumos por rango de fechas
     */
    fun getConsumptionsByDateRange(startDate: LocalDate, endDate: LocalDate): List<WaterConsumption> {
        return getAllConsumptions().filter { 
            it.date >= startDate && it.date <= endDate 
        }
    }
    
    /**
     * Obtiene el consumo de un día específico
     */
    fun getConsumptionByDate(date: LocalDate): List<WaterConsumption> {
        return getAllConsumptions().filter { it.date == date }
    }
    
    /**
     * Elimina un registro de consumo
     */
    fun deleteConsumption(id: Long) {
        val consumptions = getAllConsumptions().filter { it.id != id }
        saveAllConsumptions(consumptions)
    }
    
    /**
     * Guarda el umbral de consumo diario
     */
    fun setThreshold(liters: Double) {
        prefs.edit().putFloat(KEY_THRESHOLD, liters.toFloat()).apply()
    }
    
    /**
     * Obtiene el umbral de consumo diario
     */
    fun getThreshold(): Double {
        return prefs.getFloat(KEY_THRESHOLD, DEFAULT_THRESHOLD.toFloat()).toDouble()
    }
    
    /**
     * Limpia todos los datos
     */
    fun clearAllData() {
        prefs.edit().clear().apply()
    }
    
    private fun saveAllConsumptions(consumptions: List<WaterConsumption>) {
        val dtos = consumptions.map { WaterConsumptionDto.fromWaterConsumption(it) }
        val json = gson.toJson(dtos)
        prefs.edit().putString(KEY_CONSUMPTIONS, json).apply()
    }
    
    private fun getNextId(): Long {
        return prefs.getLong(KEY_NEXT_ID, 1L)
    }
    
    private fun incrementNextId() {
        val nextId = getNextId() + 1
        prefs.edit().putLong(KEY_NEXT_ID, nextId).apply()
    }
}

/**
 * DTO para serializar WaterConsumption a JSON
 */
private data class WaterConsumptionDto(
    val id: Long,
    val date: String,
    val liters: Double,
    val activity: String,
    val notes: String
) {
    fun toWaterConsumption(): WaterConsumption {
        return WaterConsumption(
            id = id,
            date = LocalDate.parse(date),
            liters = liters,
            activity = activity,
            notes = notes
        )
    }
    
    companion object {
        fun fromWaterConsumption(consumption: WaterConsumption): WaterConsumptionDto {
            return WaterConsumptionDto(
                id = consumption.id,
                date = consumption.date.toString(),
                liters = consumption.liters,
                activity = consumption.activity,
                notes = consumption.notes
            )
        }
    }
}
