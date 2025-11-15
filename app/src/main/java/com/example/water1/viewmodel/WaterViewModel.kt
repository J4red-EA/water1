package com.example.water1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.water1.data.WaterRepository
import com.example.water1.model.DailyConsumption
import com.example.water1.model.WaterConsumption
import com.example.water1.model.WaterStats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

/**
 * ViewModel principal para gestionar el estado de la aplicación
 */
class WaterViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository = WaterRepository(application)
    
    private val _consumptions = MutableStateFlow<List<WaterConsumption>>(emptyList())
    val consumptions: StateFlow<List<WaterConsumption>> = _consumptions.asStateFlow()
    
    private val _weeklyStats = MutableStateFlow<WaterStats?>(null)
    val weeklyStats: StateFlow<WaterStats?> = _weeklyStats.asStateFlow()
    
    private val _monthlyStats = MutableStateFlow<WaterStats?>(null)
    val monthlyStats: StateFlow<WaterStats?> = _monthlyStats.asStateFlow()
    
    private val _threshold = MutableStateFlow(150.0)
    val threshold: StateFlow<Double> = _threshold.asStateFlow()
    
    private val _todayConsumption = MutableStateFlow(0.0)
    val todayConsumption: StateFlow<Double> = _todayConsumption.asStateFlow()
    
    private val _showAlert = MutableStateFlow(false)
    val showAlert: StateFlow<Boolean> = _showAlert.asStateFlow()
    
    init {
        loadData()
    }
    
    /**
     * Carga todos los datos desde el repositorio
     */
    fun loadData() {
        viewModelScope.launch {
            _consumptions.value = repository.getAllConsumptions()
            _threshold.value = repository.getThreshold()
            updateStats()
            checkTodayConsumption()
        }
    }
    
    /**
     * Guarda un nuevo registro de consumo
     */
    fun saveConsumption(liters: Double, activity: String, notes: String, date: LocalDate = LocalDate.now()) {
        viewModelScope.launch {
            val consumption = WaterConsumption(
                date = date,
                liters = liters,
                activity = activity,
                notes = notes
            )
            repository.saveConsumption(consumption)
            loadData()
        }
    }
    
    /**
     * Elimina un registro de consumo
     */
    fun deleteConsumption(id: Long) {
        viewModelScope.launch {
            repository.deleteConsumption(id)
            loadData()
        }
    }
    
    /**
     * Actualiza el umbral de alerta
     */
    fun updateThreshold(newThreshold: Double) {
        viewModelScope.launch {
            repository.setThreshold(newThreshold)
            _threshold.value = newThreshold
            checkTodayConsumption()
        }
    }
    
    /**
     * Actualiza las estadísticas semanales y mensuales
     */
    private fun updateStats() {
        val today = LocalDate.now()
        
        // Estadísticas semanales (últimos 7 días)
        val weekStart = today.minusDays(6)
        val weekConsumptions = repository.getConsumptionsByDateRange(weekStart, today)
        _weeklyStats.value = calculateStats(weekConsumptions, weekStart, today)
        
        // Estadísticas mensuales (últimos 30 días)
        val monthStart = today.minusDays(29)
        val monthConsumptions = repository.getConsumptionsByDateRange(monthStart, today)
        _monthlyStats.value = calculateStats(monthConsumptions, monthStart, today)
    }
    
    /**
     * Calcula estadísticas para un rango de fechas
     */
    private fun calculateStats(consumptions: List<WaterConsumption>, startDate: LocalDate, endDate: LocalDate): WaterStats {
        val groupedByDate = consumptions.groupBy { it.date }
        val dailyTotals = groupedByDate.mapValues { entry ->
            entry.value.sumOf { it.liters }
        }
        
        val totalLiters = dailyTotals.values.sum()
        val daysRecorded = dailyTotals.size
        val averageDaily = if (daysRecorded > 0) totalLiters / daysRecorded else 0.0
        val maxDaily = dailyTotals.values.maxOrNull() ?: 0.0
        val minDaily = dailyTotals.values.minOrNull() ?: 0.0
        
        // Crear lista de consumo diario para el gráfico
        val weeklyData = mutableListOf<DailyConsumption>()
        var currentDate = startDate
        while (!currentDate.isAfter(endDate)) {
            val dayName = currentDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            val dateStr = "${currentDate.dayOfMonth}/${currentDate.monthValue}"
            val liters = dailyTotals[currentDate] ?: 0.0
            
            weeklyData.add(DailyConsumption(dayName, dateStr, liters))
            currentDate = currentDate.plusDays(1)
        }
        
        return WaterStats(
            totalLiters = totalLiters,
            averageDaily = averageDaily,
            maxDaily = maxDaily,
            minDaily = minDaily,
            daysRecorded = daysRecorded,
            weeklyData = weeklyData
        )
    }
    
    /**
     * Verifica el consumo de hoy y activa alerta si es necesario
     */
    private fun checkTodayConsumption() {
        val today = LocalDate.now()
        val todayConsumptions = repository.getConsumptionByDate(today)
        val total = todayConsumptions.sumOf { it.liters }
        
        _todayConsumption.value = total
        _showAlert.value = total > _threshold.value
    }
    
    /**
     * Descarta la alerta actual
     */
    fun dismissAlert() {
        _showAlert.value = false
    }
    
    /**
     * Limpia todos los datos
     */
    fun clearAllData() {
        viewModelScope.launch {
            repository.clearAllData()
            loadData()
        }
    }
}
