# Guía de Uso - Cuidado Responsable del Agua 💧

## 🎓 Para Estudiantes

Esta guía te ayudará a comprender y ejecutar el proyecto "Cuidado Responsable del Agua".

## 📱 Funcionalidades de la Aplicación

### 1. Pantalla de Inicio (Home)

**¿Qué hace?**
- Muestra el consumo total del día actual
- Presenta una barra de progreso que indica qué tan cerca estás de tu umbral
- Lista todos los registros de consumo del día
- Muestra una recomendación ecológica personalizada

**Cómo usar:**
- Toca el botón flotante "+" para agregar un nuevo registro
- Toca el ícono de configuración (⚙️) para ajustar tu umbral diario
- Toca el ícono de basura en cada registro para eliminarlo

### 2. Agregar Registro de Consumo

**¿Qué hace?**
- Permite registrar cuánta agua consumiste en una actividad específica

**Cómo usar:**
1. Ingresa la cantidad de litros (ejemplo: 50, 150.5)
2. Selecciona la actividad del menú desplegable (Ducha, Lavado de ropa, etc.)
3. Opcionalmente, agrega notas adicionales
4. Toca "Guardar" para registrar

**Ejemplo de uso:**
- Litros: 150
- Actividad: Ducha
- Notas: Ducha de 10 minutos

### 3. Pantalla de Estadísticas

**¿Qué hace?**
- Muestra gráficos y análisis de tu consumo de agua
- Dos vistas: Semanal (7 días) y Mensual (30 días)

**Información que muestra:**
- **Total**: Suma de todos los litros consumidos
- **Promedio**: Consumo diario promedio
- **Máximo**: El día que más consumiste
- **Mínimo**: El día que menos consumiste
- **Gráfico de barras**: Visualización del consumo por día

### 4. Pantalla de Recomendaciones

**¿Qué hace?**
- Presenta 10+ consejos ecológicos para ahorrar agua
- Categoriza los consejos por tipo de actividad

**Categorías:**
- 🚿 Ducha
- 🧺 Lavado
- 🌱 Jardín
- 🍳 Cocina
- 🌍 General

### 5. Sistema de Alertas

**¿Cómo funciona?**
- Estableces un umbral (ejemplo: 150 litros/día)
- Si tu consumo diario supera ese umbral, recibes una alerta
- La tarjeta de resumen cambia de color para advertirte

**Configurar umbral:**
1. En la pantalla de inicio, toca el ícono ⚙️
2. Ingresa tu nuevo umbral en litros
3. Toca "Guardar"

## 🔧 Conceptos Técnicos Implementados

### Programación Orientada a Objetos (POO)

**1. Clases de Datos (Data Classes)**
```kotlin
// Representan entidades del mundo real
data class WaterConsumption(...) // Registro de consumo
data class WaterStats(...)        // Estadísticas
data class EcoTip(...)           // Recomendación
```

**2. Enumeraciones (Enum)**
```kotlin
enum class TipCategory {
    GENERAL, SHOWER, WASHING, GARDEN, KITCHEN, ALERT
}
```

**3. Object (Singleton)**
```kotlin
object EcoTipsProvider {
    // Único proveedor de consejos en toda la app
}
```

### Arquitectura MVVM

**Model (Modelo)**: `model/` - Define las estructuras de datos
**View (Vista)**: `ui/screens/` - Interfaces de usuario con Compose
**ViewModel**: `viewmodel/` - Lógica de negocio y gestión de estado

### Persistencia de Datos

**WaterRepository.kt**
- Guarda datos en SharedPreferences (almacenamiento local del dispositivo)
- Usa Gson para convertir objetos Kotlin a JSON y viceversa
- Los datos persisten aunque cierres la aplicación

### Jetpack Compose

**Composables**: Funciones que definen la UI
```kotlin
@Composable
fun HomeScreen(...) { ... }
```

**State Management**: Uso de StateFlow para actualizar la UI automáticamente
```kotlin
val consumptions by viewModel.consumptions.collectAsState()
```

## 📊 Flujo de Datos

```
Usuario → UI (Compose) → ViewModel → Repository → SharedPreferences
                ↑                                          ↓
                └──────── StateFlow actualiza UI ─────────┘
```

## 🎯 Ejercicios Sugeridos para Aprender

### Nivel Básico
1. **Cambiar colores del tema**: Modifica `Color.kt` en `ui/theme/`
2. **Agregar un nuevo consejo**: Añade un EcoTip en `EcoTipsProvider.kt`
3. **Cambiar el umbral predeterminado**: Modifica `DEFAULT_THRESHOLD` en `WaterRepository.kt`

### Nivel Intermedio
4. **Agregar nueva categoría de actividad**: 
   - Añade opción en `AddConsumptionScreen.kt`
   - Actualiza el modelo si es necesario
5. **Cambiar formato de fecha**: Modifica los `DateTimeFormatter` en las pantallas
6. **Agregar animaciones**: Usa `AnimatedVisibility` en los composables

### Nivel Avanzado
7. **Implementar modo oscuro**: Actualiza `Theme.kt`
8. **Agregar filtros en estadísticas**: Filtrar por actividad específica
9. **Exportar datos a CSV**: Crear función en Repository
10. **Notificaciones**: Alertar al usuario cuando supere el umbral

## 🐛 Resolución de Problemas

### El proyecto no compila
1. Verifica que Android Studio esté actualizado
2. Sincroniza Gradle: File → Sync Project with Gradle Files
3. Limpia el proyecto: Build → Clean Project
4. Reconstruye: Build → Rebuild Project

### No se muestran los datos guardados
- Los datos se guardan automáticamente
- Verifica que estés usando el mismo emulador/dispositivo
- Los datos persisten por dispositivo

### Error en imports
- Asegúrate de tener todas las dependencias en `build.gradle.kts`
- Sincroniza Gradle nuevamente

## 💡 Preguntas Frecuentes

**P: ¿Los datos se sincronizan en la nube?**
R: No, los datos se guardan localmente en el dispositivo.

**P: ¿Puedo cambiar la fecha de un registro?**
R: Actualmente solo se puede registrar en la fecha actual. Esto podría ser una mejora futura.

**P: ¿Cuántos registros puedo guardar?**
R: No hay límite específico, pero depende del almacenamiento del dispositivo.

**P: ¿Cómo borro todos los datos?**
R: Puedes implementar un botón en configuración que llame a `viewModel.clearAllData()`

## 📚 Recursos Adicionales

- [Documentación de Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Kotlin para Android](https://developer.android.com/kotlin)
- [Material Design 3](https://m3.material.io/)
- [StateFlow y Flow](https://developer.android.com/kotlin/flow)

## ✅ Checklist de Evaluación

Verifica que tu proyecto cumple con:

- ✅ Registro de consumo diario de agua
- ✅ Gráficos de consumo semanal y mensual
- ✅ Alertas cuando el consumo exceda un umbral
- ✅ Recomendaciones ecológicas
- ✅ Persistencia de datos local
- ✅ Uso de POO (clases, herencia, encapsulación)
- ✅ Interfaces gráficas con Jetpack Compose
- ✅ Arquitectura MVVM
- ✅ Material Design 3
- ✅ Código documentado y organizado

## 🎓 Conceptos para la Presentación

Prepárate para explicar:

1. **POO**: Cómo usaste clases, objetos, data classes y enums
2. **MVVM**: Separación de responsabilidades (Model, View, ViewModel)
3. **Compose**: Declarative UI vs Imperative UI
4. **Persistencia**: Cómo guardas y recuperas datos
5. **StateFlow**: Gestión reactiva del estado
6. **Material Design**: Principios de diseño aplicados

---

**¡Éxito en tu proyecto!** 💪💧

Si tienes dudas, revisa el código fuente y los comentarios incluidos en cada archivo.
