# 💊 MedTrack – Aplicación Mobile

*Autores:* Agustín Codarini, Brian Ojeda  
*Fecha:* 9 de Octubre de 2025  

---

## 📱 Descripción general
*MedTrack* es una aplicación Android desarrollada en *Java (Android Studio)* bajo el patrón *MVVM*.  
Actualmente muestra una *vista unificada* con:

1. 📋 Un *listado de medicamentos* pendientes o próximos a tomar.  
2. ❤ Un *resumen visual del estado de salud* del usuario.

El objetivo del proyecto es construir un sistema simple y confiable para *registrar y seguir la medicación diaria*, junto con métricas básicas de salud personal.

---

## 🧠 Qué muestra la versión actual

### 🩺 Módulo de Medicamentos
- Lista de medicamentos cargados (mock de prueba).  
- Cada uno aparece como *tarjeta (CardView)* con:
  - Nombre del medicamento  
  - Dosis o forma de administración  
  - Indicaciones breves  
  - Hora programada  

El botón *“+” (FloatingActionButton)* permite agregar ejemplos de prueba:
- Primer clic ➜ agrega “Ejemplo A”.  
- Segundo clic ➜ agrega “Ejemplo B” y muestra un mensaje informativo.

---

### ❤ Módulo de Estado de Salud
Debajo del listado aparece un *bloque informativo* con tarjetas que resumen los datos del usuario:

| Indicador | Ejemplo |
|------------|----------|
| 🧍 Edad | 23 años |
| 📏 Altura | 172 cm |
| ⚖ Peso | 68 kg |
| 📊 IMC | 21.8 (Normal) |

Además, se incluye una *tabla de referencia de IMC*, para que el usuario pueda interpretar su estado corporal.

---

## ⚙ Tecnologías y estructura

- *Lenguaje:* Java  
- *Arquitectura:* MVVM  
- *Componentes UI:*  
  - RecyclerView  
  - CardView  
  - FloatingActionButton  
  - ConstraintLayout / NestedScrollView  
- *Reactive data:* LiveData + ViewModel  
- *Diseño visual:* Material Design  

---

## 🧩 Archivos principales

app/
├─ ui/home/HomeActivity.java → pantalla principal
├─ ui/medicamentos/MedicamentosAdapter.java
├─ viewmodel/MedicamentosViewModel.java
└─ res/layout/
├─ activity_home.xml → vista con lista + bloque de salud
├─ item_medicamento_card.xml → tarjeta individual de medicamento
└─ layout_resumen_salud.xml → bloque con edad, peso, altura e IMC

--

## 🚀 Próximos pasos
- Guardar los medicamentos de forma persistente.  
- Mostrar métricas de salud dinámicas.  
- Agregar navegación entre secciones (historial, perfil, métricas).  

---

## 📄 Repositorio del proyecto
🔗 [github.com/Brianojd/parcial-1-am-acn4bv-codarini-ojeda](https://github.com/Brianojd/parcial-1-pd-acn4bv-codarini-ojeda)

---

> 🧩 Esta versión del sistema es una *demo funcional* del MVP:  
> muestra el listado de medicamentos y el estado de salud en una sola pantalla, priorizando la claridad visual y la experiencia del usuario.
