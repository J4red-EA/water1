# 🚀 Quick Start - Inicio Rápido

## Para Comenzar Inmediatamente

### 1️⃣ Sincronizar Proyecto
```
En Android Studio:
File → Sync Project with Gradle Files
```
Espera a que descargue todas las dependencias (puede tomar 2-5 minutos la primera vez)

### 2️⃣ Ejecutar la Aplicación
```
Presiona el botón verde ▶️ (Run)
o
Shift + F10 (Windows)
```

### 3️⃣ Primera Prueba
1. La app abrirá mostrando la pantalla de **Inicio**
2. Toca el botón **"+"** (flotante, abajo a la derecha)
3. Ingresa un consumo de ejemplo:
   - Litros: `150`
   - Actividad: `Ducha`
   - Notas: `Prueba inicial`
4. Toca **"Guardar"**
5. ¡Verás tu primer registro!

---

## 🎯 Tour Rápido de la App

### Pantalla 1: Inicio 🏠
**Ubicación**: Tab inferior izquierdo

**Qué verás**:
- Tarjeta azul con el consumo total del día
- Barra de progreso hacia el umbral
- Recomendación ecológica personalizada
- Lista de registros de hoy

**Qué hacer**:
- ➕ **Botón "+"**: Agregar nuevo registro
- ⚙️ **Engranaje**: Configurar umbral (default: 150L)
- 🗑️ **Basura**: Eliminar un registro

---

### Pantalla 2: Estadísticas 📊
**Ubicación**: Tab inferior centro

**Qué verás**:
- Pestañas: **Semanal** | **Mensual**
- 4 tarjetas con números: Total, Promedio, Máximo, Mínimo
- Gráfico de barras con consumo por día
- Lista detallada de días con consumo

**Consejo**: Registra varios días para ver el gráfico completo

---

### Pantalla 3: Consejos 💡
**Ubicación**: Tab inferior derecho

**Qué verás**:
- 10+ recomendaciones ecológicas
- Iconos por categoría (ducha, lavado, jardín, cocina)
- Descripciones de cada consejo

**Categorías**:
- 🚿 Ducha
- 🧺 Lavado
- 🌱 Jardín
- 🍳 Cocina
- 🌍 General

---

## ⚡ Funcionalidades Clave

### 🎚️ Sistema de Alertas
**Cómo activarlo**:
1. Toca ⚙️ en la pantalla de inicio
2. Establece un umbral bajo (ej: `100` litros)
3. Registra un consumo que lo supere (ej: `150` litros)
4. Verás una alerta ⚠️

**Efecto visual**:
- La tarjeta de resumen cambia a color rojo
- Aparece un diálogo de alerta

---

### 📈 Gráficos Automáticos
Los gráficos se actualizan automáticamente:
- Registra consumos diferentes cada día
- Ve a la pestaña **Estadísticas**
- Alterna entre **Semanal** y **Mensual**
- El gráfico muestra barras proporcionales

---

### 🔄 Persistencia de Datos
**Prueba esto**:
1. Registra varios consumos
2. Cierra completamente la app
3. Abre la app nuevamente
4. ✅ Todos tus datos siguen ahí

---

## 🐛 Solución de Problemas Comunes

### ❌ El botón "+" no aparece o no funciona
**Causa**: Problema de Scaffold anidado (ya corregido)
**Solución**:
```
1. File → Sync Project with Gradle Files
2. Build → Clean Project
3. Build → Rebuild Project
4. Run → Run 'app'
```

### ❌ Error: "Cannot resolve symbol..."
**Solución**:
```
File → Invalidate Caches → Invalidate and Restart
```

### ❌ Error: "Unresolved reference 'SmallTopAppBar'"
**Causa**: API no existe en Material 3
**Solución**: Ya corregido - se usa `TopAppBar` en su lugar

### ❌ Error: "nativeCanvas" en StatsScreen
**Causa**: Acceso incorrecto al canvas nativo
**Solución**: Ya corregido - se usa `drawIntoCanvas` correctamente

### ❌ Gradle sync failed
**Solución**:
```
Build → Clean Project
Build → Rebuild Project
```

### ❌ La app no instala en el emulador
**Verificar**:
1. Emulador con API 28 o superior
2. Espacio suficiente en el emulador
3. Reiniciar el emulador

---

## 📱 Datos de Ejemplo para Demo

### Lunes
- Ducha: 120L
- Lavado de platos: 30L
- Total: 150L

### Martes
- Ducha: 100L
- Lavado de ropa: 80L
- Riego: 40L
- Total: 220L ⚠️ (Excede umbral)

### Miércoles
- Ducha: 90L
- Lavado de platos: 25L
- Cocina: 15L
- Total: 130L

### Jueves
- Ducha: 85L
- Lavado de platos: 20L
- Total: 105L

### Viernes
- Ducha: 95L
- Lavado de ropa: 75L
- Limpieza: 35L
- Total: 205L ⚠️

---

## 🎓 Para la Presentación

### Orden Sugerido de Demo
1. **Inicio**: Mostrar pantalla principal vacía
2. **Registro**: Agregar 2-3 consumos
3. **Alertas**: Configurar umbral y demostrar alerta
4. **Estadísticas**: Mostrar gráficos (con datos previos)
5. **Consejos**: Recorrer recomendaciones
6. **Persistencia**: Cerrar y reabrir app

### Puntos Técnicos a Mencionar
- ✅ Arquitectura MVVM
- ✅ Jetpack Compose (UI declarativa)
- ✅ StateFlow (gestión de estado reactiva)
- ✅ SharedPreferences + Gson (persistencia)
- ✅ Canvas API (gráfico personalizado)
- ✅ Material Design 3

---

## 📋 Checklist Pre-Demo

Antes de presentar, verifica:

- [ ] El proyecto compila sin errores
- [ ] Tienes datos de ejemplo registrados
- [ ] Probaste todas las pantallas
- [ ] Probaste el sistema de alertas
- [ ] El gráfico muestra información
- [ ] La persistencia funciona (cerrar/abrir app)

---

## 💡 Tips Pro

### Agregar Datos Rápido
En lugar de usar la UI, puedes modificar temporalmente el código para insertar datos:

```kotlin
// En WaterViewModel.init {}
repeat(7) { day ->
    saveConsumption(
        liters = (100..200).random().toDouble(),
        activity = "Ducha",
        notes = "Día $day",
        date = LocalDate.now().minusDays(day.toLong())
    )
}
```

### Ver Logs
En Android Studio, abre:
```
View → Tool Windows → Logcat
```
Filtra por: `com.example.water1`

---

## 🎯 Objetivos de Aprendizaje Cubiertos

- ✅ Programación Orientada a Objetos
- ✅ Kotlin para Android
- ✅ Jetpack Compose
- ✅ Arquitectura de Aplicaciones
- ✅ Persistencia de Datos
- ✅ Material Design
- ✅ Gestión de Estado
- ✅ UI/UX Design

---

## 📞 Estructura de Soporte

**Documentación completa**:
- `README.md` - Visión general del proyecto
- `GUIA_DE_USO.md` - Manual de usuario detallado
- `NOTAS_TECNICAS.md` - Detalles de implementación
- `CHECKLIST.md` - Verificación de requisitos
- `QUICK_START.md` - Este archivo

---

## 🚀 ¡Listo para Usar!

El proyecto está **100% funcional y completo**.

**Siguiente paso**: 
```
▶️ Run 'app'
```

**¡Mucha suerte con tu proyecto! 💧🌍**

---

*Si encuentras algún problema, revisa la sección de solución de problemas o consulta NOTAS_TECNICAS.md*
