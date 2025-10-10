# 💊 MedTrack Android (Demo Parcial)

Aplicación Android desarrollada en **Java**, siguiendo el patrón **MVVM (Model - View - ViewModel)**.  
Este proyecto forma parte de una práctica académica y muestra la estructura base de un sistema para **gestionar medicamentos y métricas de salud** del usuario.

---

## 📱 Descripción general

El sistema permite visualizar una lista de **medicamentos pendientes de iniciar**, con la posibilidad de agregar ejemplos de prueba mediante un botón flotante (FAB).  
Además, en la parte inferior se muestra un **resumen visual de salud**, con información como edad, altura, peso e índice de masa corporal (IMC).

---

## 🧩 Estructura del proyecto
app/
├─ java/com/codarini/ojeda/parcial1/
│ ├─ model/ → Modelos de datos (MedicamentoItem)
│ ├─ data/ → Fuente de datos (mock)
│ ├─ viewmodel/ → Lógica y estado de la interfaz (MedicamentosViewModel)
│ └─ ui/
│ ├─ home/ → Activity principal y layout del listado
│ └─ medicamentos/ → Adapter de los medicamentos
└─ res/layout/ → Interfaces XML
├─ activity_home.xml
├─ item_medicamento_card.xml
└─ layout_resumen_salud.xml


---

## ⚙️ Funcionalidades actuales

- 📋 **Listado de medicamentos** no iniciados (mock local).  
- ➕ **Botón flotante (+)**  
  - Primer clic → agrega “Ejemplo A” (solo una vez).  
- 🧠 **Arquitectura MVVM**  
  - El ViewModel controla el estado (LiveData) y comunica los datos con la UI.  
- 💾 **Datos simulados** (sin conexión a base de datos).  
- 📊 **Resumen de salud** con tarjetas de Edad, Altura, Peso e IMC.

---

## 🖼️ Captura visual (vista actual)

*(Agregar imagen o GIF de la app si se desea)*

---

## 🧠 Tecnologías utilizadas

- Java  
- AndroidX / Material Design  
- RecyclerView + Adapter  
- LiveData / ViewModel  
- ConstraintLayout + NestedScrollView  
- CardView y LinearLayout  

---

## 🚀 Próximos pasos

- Conectar con base de datos (Room o API REST).  
- Permitir registrar tomas de medicamentos.  
- Actualizar métricas de salud dinámicamente.  

---

## 📄 Autores

Desarrollado por **Brian Ojeda** y **Agustín Hernán Codarini**  
Proyecto académico — Android Studio
