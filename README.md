# Cuidado Responsable del Agua 💧

Aplicación móvil Android desarrollada en Kotlin que promueve la concientización y el uso responsable del recurso hídrico.

## 📋 Descripción

Esta aplicación permite a los usuarios registrar su consumo diario de agua, analizar estadísticas y recibir recomendaciones personalizadas sobre cómo reducir el desperdicio. Desarrollada con Jetpack Compose y siguiendo principios de programación orientada a objetos.

## ✨ Funcionalidades Implementadas

### 1. ✅ Registro de Consumo Diario de Agua
- Interfaz intuitiva para registrar el consumo de agua en litros
- Categorización por actividad (ducha, lavado, riego, cocina, etc.)
- Campo opcional para notas adicionales
- Visualización inmediata del consumo del día actual

### 2. 📊 Gráficos de Consumo Semanal y Mensual
- **Vista Semanal**: Gráfico de barras con consumo de los últimos 7 días
- **Vista Mensual**: Estadísticas de los últimos 30 días
- Métricas incluidas:
  - Total de litros consumidos
  - Promedio diario
  - Consumo máximo y mínimo
  - Listado detallado por día

### 3. ⚠️ Alertas de Consumo Excesivo
- Configuración personalizable del umbral diario
- Alerta automática cuando se supera el límite establecido
- Indicador visual con barra de progreso
- Color dinámico según el nivel de consumo

### 4. 💡 Recomendaciones Ecológicas
- 10+ consejos prácticos para ahorrar agua
- Categorización por actividad (ducha, lavado, jardín, cocina)
- Recomendaciones personalizadas según el consumo diario
- Pantalla dedicada con todos los consejos disponibles

### 5. 💾 Persistencia de Datos Local
- Almacenamiento local usando SharedPreferences
- Serialización JSON con Gson
- Historial completo de consumos
- Configuración de umbral personalizado

## 🏗️ Arquitectura del Proyecto

```
com.example.water1/
├── data/
│   └── WaterRepository.kt          # Gestión de persistencia de datos
├── model/
│   ├── WaterConsumption.kt         # Modelo de registro de consumo
│   ├── WaterStats.kt               # Modelo de estadísticas
│   └── EcoTip.kt                   # Modelo de recomendaciones
├── viewmodel/
│   └── WaterViewModel.kt           # Lógica de negocio y estado
├── ui/
│   ├── screens/
│   │   ├── HomeScreen.kt           # Pantalla principal
│   │   ├── AddConsumptionScreen.kt # Registro de consumo
│   │   ├── StatsScreen.kt          # Estadísticas y gráficos
│   │   └── TipsScreen.kt           # Recomendaciones
│   └── theme/                      # Temas y estilos
├── utils/
│   └── EcoTipsProvider.kt          # Proveedor de consejos ecológicos
└── MainActivity.kt                 # Actividad principal
```

## 🛠️ Tecnologías Utilizadas

- **Lenguaje**: Kotlin
- **UI Framework**: Jetpack Compose
- **Arquitectura**: MVVM (Model-View-ViewModel)
- **Persistencia**: SharedPreferences + JSON (Gson)
- **Gestión de Estado**: StateFlow
- **Material Design**: Material 3

## 📱 Pantallas de la Aplicación

### Inicio 🏠
- Resumen del consumo diario actual
- Lista de registros del día
- Recomendación personalizada
- Indicador visual de umbral
- Botón de acción flotante para agregar registro

### Estadísticas 📈
- Pestañas de vista semanal y mensual
- Gráfico de barras interactivo
- Tarjetas de resumen con métricas clave
- Listado detallado de consumo por día

### Consejos 💡
- Categorización de recomendaciones
- Iconos intuitivos por categoría
- Descripciones detalladas de cada consejo
- Interfaz visual atractiva

### Registro de Consumo ➕
- Formulario simple e intuitivo
- Selector desplegable de actividades
- Validación de datos
- Confirmación visual

## 🚀 Cómo Ejecutar el Proyecto

1. **Clonar el repositorio**
   ```bash
   git clone [URL_DEL_REPOSITORIO]
   ```

2. **Abrir en Android Studio**
   - Android Studio Hedgehog o superior
   - SDK mínimo: API 28 (Android 9.0)
   - SDK objetivo: API 36

3. **Sincronizar Gradle**
   - El proyecto descargará automáticamente las dependencias

4. **Ejecutar en emulador o dispositivo**
   - Configurar un emulador con API 28 o superior
   - O conectar un dispositivo físico con depuración USB habilitada

## 📦 Dependencias Principales

```kotlin
// ViewModel y Lifecycle
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

// Serialización JSON
implementation("com.google.code.gson:gson:2.10.1")

// Material Icons Extended
implementation("androidx.compose.material:material-icons-extended:1.6.1")
```

## 🎯 Competencias Desarrolladas

- ✅ Programación orientada a objetos en Kotlin
- ✅ Desarrollo de interfaces gráficas con Jetpack Compose
- ✅ Arquitectura MVVM y gestión de estado
- ✅ Persistencia de datos local
- ✅ Visualización de datos con gráficos personalizados
- ✅ Material Design 3
- ✅ Manejo de formularios y validación
- ✅ Navegación entre pantallas

## 👥 Equipo de Desarrollo

Este proyecto fue diseñado para trabajo en equipo de 2-3 estudiantes.

## 📄 Licencia

Proyecto educativo desarrollado para el curso de Desarrollo de Aplicaciones Móviles.

## 💧 Impacto Ambiental

Esta aplicación contribuye a:
- Concientización sobre el uso responsable del agua
- Reducción del consumo mediante seguimiento y análisis
- Educación sobre prácticas sustentables
- Fomento de hábitos ecológicos

---

**Desarrollado con ❤️ para promover el cuidado del agua** 🌍💧
