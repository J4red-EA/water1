# Notas de Implementación Técnica 🔧

## Estructura del Proyecto

### Paquete `model/`

#### WaterConsumption.kt
- **Propósito**: Representa un registro individual de consumo de agua
- **Atributos**:
  - `id`: Identificador único autogenerado
  - `date`: Fecha del consumo (LocalDate)
  - `liters`: Cantidad en litros (Double)
  - `activity`: Tipo de actividad (String)
  - `notes`: Notas opcionales (String)

#### WaterStats.kt
- **Propósito**: Encapsula estadísticas calculadas de consumo
- **Uso**: Se calcula dinámicamente en el ViewModel
- **Incluye**: Total, promedio, máximo, mínimo y datos diarios para gráficos

#### EcoTip.kt
- **Propósito**: Representa una recomendación ecológica
- **Enum TipCategory**: Categorización de consejos

---

### Paquete `data/`

#### WaterRepository.kt
**Patrón de Diseño**: Repository Pattern

**Responsabilidades**:
1. Abstrae la fuente de datos (SharedPreferences)
2. Maneja serialización/deserialización JSON
3. Proporciona API simple para el ViewModel

**Métodos principales**:
- `saveConsumption()`: Guarda nuevo registro
- `getAllConsumptions()`: Recupera todos los registros
- `getConsumptionsByDateRange()`: Filtra por fechas
- `setThreshold()` / `getThreshold()`: Maneja umbral

**Implementación de Persistencia**:
- Usa SharedPreferences para almacenamiento key-value
- Gson convierte List<WaterConsumption> ↔ JSON String
- DTO (WaterConsumptionDto) para manejar serialización de LocalDate

**¿Por qué DTO?**
- LocalDate no es serializable directamente por Gson
- Convertimos LocalDate ↔ String en el DTO
- Separación de concerns: modelo de dominio vs modelo de persistencia

---

### Paquete `viewmodel/`

#### WaterViewModel.kt
**Patrón de Diseño**: MVVM Architecture

**Responsabilidades**:
1. Contiene la lógica de negocio
2. Gestiona el estado de la UI mediante StateFlow
3. Coordina entre Repository y las pantallas
4. Realiza cálculos de estadísticas

**StateFlow vs LiveData**:
- StateFlow es más moderno y compatible con Compose
- Integración nativa con `collectAsState()` en Compose
- Mejor para concurrencia con Coroutines

**Flujos de Estado**:
```kotlin
_consumptions → consumptions (StateFlow)
_weeklyStats → weeklyStats (StateFlow)
_monthlyStats → monthlyStats (StateFlow)
_threshold → threshold (StateFlow)
_todayConsumption → todayConsumption (StateFlow)
_showAlert → showAlert (StateFlow)
```

**Método clave: calculateStats()**
- Agrupa consumos por fecha
- Calcula totales diarios
- Genera lista completa de días (incluso sin datos)
- Formatea nombres de días para el gráfico

---

### Paquete `ui/screens/`

#### HomeScreen.kt
**Componentes principales**:
- `DailySummaryCard`: Muestra consumo actual con barra de progreso
- `EcoTipCard`: Presenta recomendación del día
- `ConsumptionCard`: Item individual de registro con opción de eliminar
- `ThresholdDialog`: Popup para configurar umbral

**Arquitectura de UI**:
- **Sin Scaffold interno**: Se usa Column + TopAppBar directamente
- **FAB en MainActivity**: El botón flotante está en el Scaffold del MainActivity
- **Razón**: Evitar conflictos de Scaffolds anidados que ocultaban el FAB

**Estado local vs global**:
- Estado global: `consumptions`, `todayConsumption` (desde ViewModel)
- Estado local: `showThresholdDialog`, `showDeleteDialog` (específico de UI)

#### AddConsumptionScreen.kt
**Validación**:
- Verifica que liters sea un número válido > 0
- Muestra error visual si falla
- Previene guardado de datos inválidos

**ExposedDropdownMenuBox**:
- Componente de Material 3 para selección
- Lista predefinida de actividades
- Fácil de extender con más opciones

#### StatsScreen.kt
**Características**:
- TabRow para alternar entre semanal/mensual
- Reutiliza `StatsContent()` para ambas vistas
- Estado vacío manejado elegantemente

**BarChart (Custom Canvas)**:
- Dibujado personalizado con Canvas API
- Calcula altura de barras proporcionalmente
- Etiquetas con nombres de días y valores
- Responsive al tamaño de la pantalla

**Cálculos importantes**:
```kotlin
barHeight = (dayData.liters / maxValue * chartHeight)
x = index * (barWidth + spacing) + spacing
```

#### TipsScreen.kt
**Arquitectura**:
- Consume datos de `EcoTipsProvider` (singleton)
- Mapea categorías a iconos y colores
- Card personalizado por categoría

---

### Paquete `utils/`

#### EcoTipsProvider.kt
**Patrón de Diseño**: Singleton (object)

**¿Por qué object?**
- Solo necesitamos una instancia
- Los consejos son inmutables
- Acceso global sin inyección de dependencias

**Método inteligente: getTipForConsumption()**
- Analiza el consumo diario
- Retorna consejo personalizado según nivel
- Rangos: > 200L, > 150L, > 100L, <= 100L

**Extensibilidad**:
- Fácil agregar nuevos consejos a la lista
- Categorización flexible
- Localización futura posible

---

## Decisiones de Diseño

### ¿Por qué SharedPreferences y no Room Database?

**Ventajas de SharedPreferences**:
- ✅ Implementación más simple
- ✅ Suficiente para el alcance del proyecto
- ✅ No requiere configuración compleja
- ✅ Serialización JSON flexible con Gson

**Cuándo usar Room**:
- Relaciones complejas entre tablas
- Consultas SQL avanzadas
- Datasets muy grandes (miles de registros)
- Migraciones de esquema frecuentes

### ¿Por qué Compose en lugar de XML?

**Ventajas de Compose**:
- ✅ Código declarativo más legible
- ✅ Menos boilerplate
- ✅ State management integrado
- ✅ Previews en tiempo real
- ✅ Es el futuro de UI en Android

### ¿Por qué StateFlow y no MutableState?

**StateFlow**:
- ✅ Compatible con Coroutines
- ✅ Mejor para operaciones asíncronas
- ✅ Lifecycle-aware en ViewModel
- ✅ Puede ser observado desde múltiples composables

**MutableState**:
- Para estado local simple en composables
- No sobrevive a cambios de configuración

---

## Mejoras Futuras Sugeridas

### Corto Plazo
1. **Modo oscuro**: Implementar tema dark
2. **Selección de fecha**: Permitir registrar consumos de días anteriores
3. **Edición de registros**: Modificar registros existentes
4. **Filtros en estadísticas**: Por actividad o rango personalizado

### Mediano Plazo
5. **Notificaciones**: Alertas push diarias
6. **Widgets**: Mostrar consumo en pantalla de inicio
7. **Exportar/Importar datos**: Backup en JSON/CSV
8. **Múltiples usuarios**: Perfiles familiares
9. **Comparación**: Con promedios nacionales

### Largo Plazo
10. **Cloud sync**: Firebase Firestore
11. **Machine Learning**: Predicción de consumo
12. **Gamificación**: Logros y desafíos
13. **Integración IoT**: Medidores inteligentes
14. **Red social**: Compartir logros

---

## Testing

### Casos de Prueba Sugeridos

**Repository**:
```kotlin
@Test
fun `save consumption returns valid id`()
@Test
fun `get consumptions by date filters correctly`()
@Test
fun `threshold persists after app restart`()
```

**ViewModel**:
```kotlin
@Test
fun `calculate weekly stats correctly`()
@Test
fun `alert triggers when threshold exceeded`()
@Test
fun `delete consumption updates state`()
```

**UI** (con Compose Test):
```kotlin
@Test
fun `add consumption screen validates input`()
@Test
fun `home screen displays today consumptions`()
@Test
fun `stats screen shows empty state correctly`()
```

---

## Optimizaciones de Rendimiento

### Implementadas
1. **LazyColumn**: Reciclaje de vistas para listas largas
2. **collectAsState()**: Solo recompone cuando cambia el estado
3. **remember**: Evita recreación de objetos en recomposiciones

### Potenciales
1. **Derivedstateof**: Para cálculos que dependen de múltiples estados
2. **key()**: En LazyColumn para mejor reciclaje
3. **Paging**: Si el dataset crece mucho
4. **WorkManager**: Para operaciones en background

---

## Seguridad y Privacidad

### Implementado
- ✅ Datos almacenados localmente (no en la nube)
- ✅ No se requieren permisos especiales
- ✅ No se recopila información personal

### Consideraciones
- SharedPreferences no está encriptado por defecto
- Para datos sensibles, considerar EncryptedSharedPreferences

---

## Problemas Comunes Resueltos 🔧

### 1. FAB (Floating Action Button) no visible
**Problema**: Scaffold anidado dentro de otro Scaffold
**Solución**: 
- Mover FAB al Scaffold principal en MainActivity
- HomeScreen usa Column en lugar de Scaffold
- FAB solo aparece en pantalla Home

### 2. Error "SmallTopAppBar" no existe
**Problema**: API no disponible en Material 3
**Solución**: Usar `TopAppBar` con `@OptIn(ExperimentalMaterial3Api::class)`

### 3. Error "nativeCanvas" en gráficos
**Problema**: Acceso incorrecto al canvas nativo de Android
**Solución**: 
```kotlin
drawIntoCanvas { canvas ->
    val nativeCanvas = canvas.nativeCanvas
    // usar nativeCanvas.drawText(...)
}
```

### 4. Type mismatch en sealed class Screen
**Problema**: Usar `object` en lugar de `data object`
**Solución**: Cambiar a `data object` (Kotlin 1.9+)

---

## Buenas Prácticas Aplicadas

1. **Separation of Concerns**: Cada clase tiene una responsabilidad única
2. **DRY (Don't Repeat Yourself)**: Componentes reutilizables
3. **Naming Conventions**: Nombres descriptivos y consistentes
4. **Documentation**: Comentarios KDoc en funciones públicas
5. **Immutability**: Uso de `val` y data classes
6. **Null Safety**: Aprovecha el sistema de tipos de Kotlin
7. **Resource Management**: Coroutines para operaciones asíncronas
8. **Avoid Nested Scaffolds**: Un Scaffold por nivel de navegación

---

## Recursos de Aprendizaje

### Kotlin
- [Kotlin Koans](https://play.kotlinlang.org/koans)
- [Kotlin by Example](https://play.kotlinlang.org/byExample)

### Jetpack Compose
- [Compose Pathway (Google)](https://developer.android.com/courses/pathways/compose)
- [Compose Samples](https://github.com/android/compose-samples)

### Architecture
- [Guide to App Architecture](https://developer.android.com/topic/architecture)
- [ViewModel Overview](https://developer.android.com/topic/libraries/architecture/viewmodel)

### Material Design
- [Material 3 Guidelines](https://m3.material.io/)
- [Material Components Catalog](https://material.io/components)

---

## Conclusión

Este proyecto demuestra:
- ✅ Aplicación completa y funcional
- ✅ Arquitectura limpia y escalable
- ✅ Buenas prácticas de desarrollo Android
- ✅ Uso de tecnologías modernas
- ✅ Código mantenible y documentado

**El proyecto está listo para su presentación y evaluación** ✨

---

*Última actualización: 15 de noviembre de 2025*
