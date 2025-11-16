package com.example.water1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.water1.ui.screens.*
import com.example.water1.ui.theme.Water1Theme
import com.example.water1.viewmodel.WaterViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Water1Theme {
                WaterApp()
            }
        }
    }
}

@Composable
fun WaterApp(viewModel: WaterViewModel = viewModel()) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    
    val consumptions by viewModel.consumptions.collectAsState()
    val weeklyStats by viewModel.weeklyStats.collectAsState()
    val monthlyStats by viewModel.monthlyStats.collectAsState()
    val threshold by viewModel.threshold.collectAsState()
    val todayConsumption by viewModel.todayConsumption.collectAsState()
    val showAlert by viewModel.showAlert.collectAsState()
    
    when (currentScreen) {
        Screen.Home -> {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                            label = { Text("Inicio") },
                            selected = currentScreen == Screen.Home,
                            onClick = { currentScreen = Screen.Home }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.BarChart, contentDescription = "Estadísticas") },
                            label = { Text("Estadísticas") },
                            selected = currentScreen == Screen.Stats,
                            onClick = { currentScreen = Screen.Stats }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Lightbulb, contentDescription = "Consejos") },
                            label = { Text("Consejos") },
                            selected = currentScreen == Screen.Tips,
                            onClick = { currentScreen = Screen.Tips }
                        )
                    }
                },
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        onClick = { currentScreen = Screen.AddConsumption },
                        icon = { Icon(Icons.Default.Add, contentDescription = "Agregar") },
                        text = { Text("Registrar") }
                    )
                }
            ) { padding ->
                HomeScreen(
                    consumptions = consumptions,
                    todayConsumption = todayConsumption,
                    threshold = threshold,
                    showAlert = showAlert,
                    onAddClick = { currentScreen = Screen.AddConsumption },
                    onDeleteConsumption = { id -> viewModel.deleteConsumption(id) },
                    onUpdateThreshold = { newThreshold -> viewModel.updateThreshold(newThreshold) },
                    onDismissAlert = { viewModel.dismissAlert() },
                    modifier = Modifier.padding(padding)
                )
            }
        }
        
        Screen.Stats -> {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                            label = { Text("Inicio") },
                            selected = currentScreen == Screen.Home,
                            onClick = { currentScreen = Screen.Home }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.BarChart, contentDescription = "Estadísticas") },
                            label = { Text("Estadísticas") },
                            selected = currentScreen == Screen.Stats,
                            onClick = { currentScreen = Screen.Stats }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Lightbulb, contentDescription = "Consejos") },
                            label = { Text("Consejos") },
                            selected = currentScreen == Screen.Tips,
                            onClick = { currentScreen = Screen.Tips }
                        )
                    }
                }
            ) { padding ->
                StatsScreen(
                    weeklyStats = weeklyStats,
                    monthlyStats = monthlyStats,
                    modifier = Modifier.padding(padding)
                )
            }
        }
        
        Screen.Tips -> {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                            label = { Text("Inicio") },
                            selected = currentScreen == Screen.Home,
                            onClick = { currentScreen = Screen.Home }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.BarChart, contentDescription = "Estadísticas") },
                            label = { Text("Estadísticas") },
                            selected = currentScreen == Screen.Stats,
                            onClick = { currentScreen = Screen.Stats }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Lightbulb, contentDescription = "Consejos") },
                            label = { Text("Consejos") },
                            selected = currentScreen == Screen.Tips,
                            onClick = { currentScreen = Screen.Tips }
                        )
                    }
                }
            ) { padding ->
                TipsScreen(modifier = Modifier.padding(padding))
            }
        }
        
        Screen.AddConsumption -> {
            AddConsumptionScreen(
                onSave = { liters, activity, notes ->
                    viewModel.saveConsumption(liters, activity, notes)
                },
                onBack = { currentScreen = Screen.Home }
            )
        }
    }
}

sealed class Screen {
    data object Home : Screen()
    data object Stats : Screen()
    data object Tips : Screen()
    data object AddConsumption : Screen()
}