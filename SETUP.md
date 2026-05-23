# CiberStudent App - Guía de Configuración

## ⚠️ Importante para colaboradores (GitHub)

El archivo `google-services.json` **no está incluido en el repositorio** por seguridad (contiene claves privadas de Firebase). Cada integrante del equipo debe obtenerlo antes de compilar.

Tienes dos opciones:

**Opción A — Compartir el mismo Firebase (recomendado para trabajo en equipo)**
Todos usan la misma base de datos y los mismos usuarios. El líder del proyecto debe:
1. Ir a [Firebase Console](https://console.firebase.google.com/) → proyecto `ciber-estudent`
2. ⚙️ Configuración del proyecto → **Usuarios y permisos** → Agregar miembro
3. Ingresar el correo del compañero con rol **Editor**
4. El compañero descarga `google-services.json` desde ⚙️ Configuración del proyecto → sección Tus apps
5. Lo coloca en `CiberStudentApp/app/google-services.json`

**Opción B — Firebase propio por persona (desarrollo independiente)**
Cada integrante crea su propio proyecto en Firebase, registra la app con el paquete `com.cibertec.student`, descarga su `google-services.json` y lo coloca en `app/`. Sus datos no afectan a los demás.

---

## Flujo de trabajo en GitHub

```bash
# Clonar el repositorio
git clone https://github.com/tu-usuario/CiberStudentApp.git

# Crear tu rama personal (usa tu nombre o feature)
git checkout -b feature/nombre-funcionalidad

# Colocar el google-services.json en app/ (NO se sube al repo)
# Luego abrir el proyecto en Android Studio

# Subir tus cambios
git add .
git commit -m "feat: descripción de lo que hiciste"
git push origin feature/nombre-funcionalidad
```

> El `.gitignore` ya está configurado para excluir `google-services.json`, archivos de build, `.idea/` y keystores.

---

## Requisitos previos
- Android Studio Panda (2025.3.1) o superior
- JDK 17 o superior (JDK 21 incluido en Android Studio Panda)
- Cuenta de Firebase (cuenta Google)

## Pasos para ejecutar el proyecto

### 1. Configurar Firebase

> ✅ Ya creaste el proyecto **ciber-estudent** en Firebase. Solo sigue desde el paso 3.

1. Ve a [Firebase Console](https://console.firebase.google.com/)
2. ~~Crea un nuevo proyecto~~ → Ya existe: **ciber-estudent**
3. Dentro del proyecto **ciber-estudent**, ve a ⚙️ **Configuración del proyecto**
4. En la sección **Tus apps**, haz clic en el ícono de Android (➕ Agregar app)
5. Ingresa el **nombre del paquete**: `com.cibertec.student`
6. (Opcional) Apodo de la app: `CiberStudent`
7. Haz clic en **Registrar app**
8. Descarga el archivo `google-services.json`
9. Coloca ese archivo en la carpeta: `CiberStudentApp/app/google-services.json`

### 2. Habilitar servicios en Firebase
- **Authentication** → Habilitar "Email/Password"
- **Firestore Database** → Crear base de datos en modo prueba
- **Cloud Messaging** → Habilitado por defecto

### 3. Reglas de Firestore (modo desarrollo)
```
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

### 4. Abrir en Android Studio
1. File → Open → Selecciona la carpeta `CiberStudentApp`
2. Espera la sincronización de Gradle
3. Conecta un dispositivo o crea un emulador (API 26+)
4. Run → Run 'app'

## Arquitectura del proyecto

Stack: **Kotlin · MVVM · Clean Architecture · Hilt · Room · Firebase · Coroutines + StateFlow · Navigation Component · Material Design 3**

```
com.cibertec.student/
│
├── data/                                   ← CAPA DE DATOS
│   ├── local/
│   │   ├── dao/
│   │   │   ├── CourseDao.kt
│   │   │   ├── TaskDao.kt
│   │   │   ├── NoteDao.kt
│   │   │   └── AttendanceDao.kt
│   │   ├── entity/
│   │   │   ├── CourseEntity.kt
│   │   │   ├── TaskEntity.kt
│   │   │   ├── NoteEntity.kt
│   │   │   └── AttendanceEntity.kt
│   │   ├── converter/
│   │   │   └── Converters.kt
│   │   └── database/
│   │       └── AppDatabase.kt
│   └── repository/
│       ├── AuthRepositoryImpl.kt
│       ├── CourseRepositoryImpl.kt
│       ├── TaskRepositoryImpl.kt
│       ├── NoteRepositoryImpl.kt
│       └── AttendanceRepositoryImpl.kt
│
├── domain/                                 ← CAPA DE DOMINIO
│   ├── model/
│   │   ├── User.kt
│   │   ├── Course.kt
│   │   ├── Task.kt
│   │   ├── Note.kt
│   │   └── AttendanceRecord.kt
│   └── repository/  (interfaces)
│       ├── AuthRepository.kt
│       ├── CourseRepository.kt
│       ├── TaskRepository.kt
│       ├── NoteRepository.kt
│       └── AttendanceRepository.kt
│
├── presentation/                           ← CAPA DE PRESENTACIÓN
│   ├── splash/
│   │   └── SplashActivity.kt
│   ├── auth/
│   │   ├── AuthActivity.kt
│   │   ├── AuthViewModel.kt
│   │   ├── LoginFragment.kt
│   │   └── RegisterFragment.kt
│   ├── main/
│   │   └── MainActivity.kt
│   ├── home/
│   │   ├── HomeFragment.kt
│   │   ├── HomeViewModel.kt
│   │   └── TodayCourseAdapter.kt
│   ├── schedule/
│   │   ├── ScheduleFragment.kt
│   │   ├── ScheduleViewModel.kt
│   │   ├── CourseAdapter.kt
│   │   └── AddCourseDialog.kt
│   ├── tasks/
│   │   ├── TasksFragment.kt
│   │   ├── TasksViewModel.kt
│   │   ├── TaskAdapter.kt
│   │   └── AddTaskDialog.kt
│   ├── notes/
│   │   ├── NotesFragment.kt
│   │   ├── NotesViewModel.kt
│   │   ├── NoteAdapter.kt
│   │   └── AddNoteDialog.kt
│   └── attendance/
│       ├── AttendanceFragment.kt
│       ├── AttendanceViewModel.kt
│       └── AttendanceCourseAdapter.kt
│
├── core/                                   ← UTILIDADES
│   ├── notifications/
│   │   ├── NotificationScheduler.kt
│   │   ├── ReminderReceiver.kt
│   │   ├── BootReceiver.kt
│   │   └── CiberFirebaseMessagingService.kt
│   ├── utils/
│   │   ├── DateUtils.kt
│   │   └── Extensions.kt
│   └── constants/
│       └── Constants.kt
│
├── di/                                     ← INYECCIÓN DE DEPENDENCIAS (Hilt)
│   ├── AppModule.kt
│   ├── DatabaseModule.kt
│   ├── FirebaseModule.kt
│   └── RepositoryModule.kt
│
└── CiberStudentApp.kt                      ← Application class
```

### Recursos (res/)
```
res/
├── layout/         activity_auth · activity_main · activity_splash
│                   fragment_home/schedule/tasks/notes/attendance
│                   item_course · item_task · item_note · item_today_course
│                   item_attendance_course · item_calendar_day
│                   dialog_add_course · dialog_add_task · dialog_add_note
├── drawable/       ic_logo · ic_* (íconos) · bg_* (fondos) · splash_background
├── navigation/     nav_auth.xml · nav_main.xml
├── values/         colors · strings · dimens · themes · styles
├── mipmap/         ic_launcher · ic_launcher_round (adaptive icon)
├── anim/           slide_in/out_left/right
└── menu/           bottom_nav_menu.xml
```

## Estructura de carpetas en tu computadora

Cuando abres la carpeta del proyecto en tu explorador de archivos, verás esto:

```
ciber-student\                   ← Carpeta raíz en el Escritorio
│
├── CiberStudentApp\             ← ✅ PROYECTO REAL — este abres en Android Studio
│   ├── app\                        Contiene todo el código Kotlin y recursos
│   ├── build.gradle               Dependencias y configuración del módulo app
│   ├── settings.gradle            Define el nombre del proyecto y módulos incluidos
│   ├── gradle\wrapper\            Versión de Gradle que usa el proyecto (8.11.1)
│   ├── SETUP.md                   Esta guía de configuración
│   └── .gitignore                 Archivos que NO se suben a GitHub
│
├── app\                         ← ⚠️ Proyecto vacío generado por Android Studio
├── build.gradle.kts                 automáticamente. No contiene código útil.
├── settings.gradle.kts              Puedes ignorarlo o borrarlo.
│
├── CLAUDE.md                    ← Instrucciones de desarrollo para el asistente IA
└── local.properties             ← Ruta local del SDK de Android (no se sube a GitHub)
```

> Siempre abre la carpeta `CiberStudentApp` en Android Studio, no la carpeta `ciber-student`.

---

## Descripción de cada archivo del proyecto

### Raíz del proyecto (`CiberStudentApp/`)

| Archivo | Descripción |
|---------|-------------|
| `build.gradle` (raíz) | Declara los plugins globales: AGP, Kotlin, KSP, Hilt, Firebase, Navigation SafeArgs |
| `app/build.gradle` | Define las dependencias de la app: Room, Firebase, Coil, Calendar, etc. y configuraciones de compilación |
| `settings.gradle` | Nombre del proyecto (`CiberStudent`) y módulos incluidos (`:app`) |
| `gradle-wrapper.properties` | Especifica la versión exacta de Gradle a usar (8.11.1) |
| `.gitignore` | Excluye de GitHub: `google-services.json`, carpetas `build/`, archivos `.idea/`, keystores |
| `google-services.json` | Archivo de configuración de Firebase (NO está en GitHub, cada uno lo descarga) |

---

### Capa de Datos (`data/`)

| Archivo | Descripción |
|---------|-------------|
| `AppDatabase.kt` | Clase principal de Room. Define la base de datos local SQLite con todas las tablas (Course, Task, Note, Attendance) |
| `Converters.kt` | Convierte tipos complejos (listas, fechas) a tipos que Room puede guardar en SQLite |
| `CourseDao.kt` | Interfaz con las consultas SQL para cursos: insertar, actualizar, eliminar, listar por día |
| `TaskDao.kt` | Consultas SQL para tareas: filtrar por fecha, por estado completado/pendiente |
| `NoteDao.kt` | Consultas SQL para notas: buscar por texto, ordenar por fecha de modificación |
| `AttendanceDao.kt` | Consultas SQL para asistencia: contar presencias/ausencias por curso |
| `CourseEntity.kt` | Representa la tabla `courses` en la base de datos local |
| `TaskEntity.kt` | Representa la tabla `tasks` con campos: título, fecha límite, prioridad, completado |
| `NoteEntity.kt` | Representa la tabla `notes` con campos: título, contenido, color, curso relacionado |
| `AttendanceEntity.kt` | Representa la tabla `attendance` con fecha, estado (presente/ausente) y curso |
| `AuthRepositoryImpl.kt` | Implementa el login y registro usando Firebase Authentication |
| `CourseRepositoryImpl.kt` | Lee/escribe cursos combinando Room (local) y Firestore (nube) |
| `TaskRepositoryImpl.kt` | Gestiona tareas con sincronización Room + Firestore |
| `NoteRepositoryImpl.kt` | Gestiona notas con sincronización Room + Firestore |
| `AttendanceRepositoryImpl.kt` | Gestiona registros de asistencia con sincronización Room + Firestore |

---

### Capa de Dominio (`domain/`)

| Archivo | Descripción |
|---------|-------------|
| `User.kt` | Modelo puro de un usuario: id, nombre, email, carrera |
| `Course.kt` | Modelo de un curso: nombre, profesor, aula, horario, días de la semana, color |
| `Task.kt` | Modelo de una tarea: título, descripción, fecha límite, prioridad (alta/media/baja), estado |
| `Note.kt` | Modelo de una nota: título, contenido, color de tarjeta, curso asociado, fecha |
| `AttendanceRecord.kt` | Modelo de un registro de asistencia: fecha, estado, porcentaje acumulado |
| `AuthRepository.kt` | Interfaz que define: `login()`, `register()`, `logout()`, `getCurrentUser()` |
| `CourseRepository.kt` | Interfaz que define: `getCourses()`, `addCourse()`, `updateCourse()`, `deleteCourse()` |
| `TaskRepository.kt` | Interfaz que define: `getTasks()`, `addTask()`, `completeTask()`, `deleteTask()` |
| `NoteRepository.kt` | Interfaz que define: `getNotes()`, `addNote()`, `updateNote()`, `deleteNote()`, `search()` |
| `AttendanceRepository.kt` | Interfaz que define: `getAttendance()`, `registerAttendance()`, `getPercentage()` |

---

### Capa de Presentación (`presentation/`)

| Archivo | Descripción |
|---------|-------------|
| `SplashActivity.kt` | Pantalla de splash con logo animado. Redirige a Auth o Main según si hay sesión activa |
| `AuthActivity.kt` | Activity contenedor del flujo de autenticación (Login y Register) |
| `AuthViewModel.kt` | Maneja el estado del login y registro. Expone StateFlow con resultado de autenticación |
| `LoginFragment.kt` | Pantalla de inicio de sesión con email y contraseña. Valida campos antes de llamar al ViewModel |
| `RegisterFragment.kt` | Pantalla de registro. Valida que las contraseñas coincidan y llama a Firebase Auth |
| `MainActivity.kt` | Activity principal con BottomNavigationView y NavHostFragment para los 5 módulos |
| `HomeFragment.kt` | Dashboard: muestra cursos del día, tareas pendientes próximas y resumen de asistencia |
| `HomeViewModel.kt` | Obtiene y combina datos de cursos y tareas para el dashboard |
| `TodayCourseAdapter.kt` | Adapter del RecyclerView de cursos del día en el Home |
| `ScheduleFragment.kt` | Pantalla de horarios con calendario semanal (KizitoNwose) y lista de cursos por día |
| `ScheduleViewModel.kt` | Carga cursos filtrados por día de la semana seleccionado |
| `CourseAdapter.kt` | Adapter del RecyclerView de cursos con swipe para eliminar |
| `AddCourseDialog.kt` | BottomSheet para agregar o editar un curso: nombre, profesor, aula, días, hora |
| `TasksFragment.kt` | Pantalla de tareas con filtros por estado (todas/pendientes/completadas) |
| `TasksViewModel.kt` | Filtra tareas, marca como completadas y programa recordatorios con AlarmManager |
| `TaskAdapter.kt` | Adapter de tareas con checkbox para marcar como completada y chip de prioridad |
| `AddTaskDialog.kt` | BottomSheet para agregar tarea: título, descripción, fecha límite, prioridad |
| `NotesFragment.kt` | Pantalla de notas en grilla de 2 columnas con buscador en tiempo real |
| `NotesViewModel.kt` | Filtra notas por texto de búsqueda con debounce de 300ms |
| `NoteAdapter.kt` | Adapter con ListAdapter + DiffUtil para grilla de tarjetas de notas con color |
| `AddNoteDialog.kt` | BottomSheet para crear o editar una nota con selector de color |
| `AttendanceFragment.kt` | Pantalla de asistencia con porcentaje por curso y botones Presente/Ausente |
| `AttendanceViewModel.kt` | Calcula el porcentaje de asistencia y lanza alerta si baja del 70% |
| `AttendanceCourseAdapter.kt` | Adapter de cursos en la pantalla de asistencia con barra de progreso |

---

### Core (`core/`)

| Archivo | Descripción |
|---------|-------------|
| `NotificationScheduler.kt` | Programa alarmas locales con AlarmManager para recordar tareas antes de su vencimiento |
| `ReminderReceiver.kt` | BroadcastReceiver que recibe la alarma y muestra la notificación en la barra de estado |
| `BootReceiver.kt` | BroadcastReceiver que re-programa las alarmas cuando el teléfono se reinicia |
| `CiberFirebaseMessagingService.kt` | Recibe notificaciones push de Firebase Cloud Messaging (FCM) cuando la app está en segundo plano |
| `DateUtils.kt` | Funciones utilitarias: formatear fechas, calcular diferencia de días, obtener día de la semana |
| `Extensions.kt` | Funciones de extensión Kotlin: `View.visible()`, `View.gone()`, `String.isValidEmail()`, etc. |
| `Constants.kt` | Constantes globales: nombres de colecciones Firestore, canal de notificaciones, claves de SharedPreferences |

---

### DI — Inyección de Dependencias (`di/`)

| Archivo | Descripción |
|---------|-------------|
| `AppModule.kt` | Provee dependencias generales: Context, SharedPreferences, NotificationScheduler |
| `DatabaseModule.kt` | Crea e inyecta la instancia única de Room (`AppDatabase`) y todos los DAOs |
| `FirebaseModule.kt` | Crea e inyecta las instancias de `FirebaseAuth` y `FirebaseFirestore` |
| `RepositoryModule.kt` | Vincula las interfaces de dominio (`CourseRepository`) con sus implementaciones (`CourseRepositoryImpl`) |

---

### Recursos (`res/`)

| Archivo / Carpeta | Descripción |
|-------------------|-------------|
| `colors.xml` | Paleta de colores institucional azul: primary, accent, surface, background, text |
| `themes.xml` | Tema principal Material Design 3 y estilos reutilizables: botones, cards, inputs, toolbar |
| `strings.xml` | Todos los textos de la app centralizados para fácil traducción |
| `dimens.xml` | Espaciados y tamaños estándar del proyecto |
| `nav_auth.xml` | Grafo de navegación del flujo de autenticación: Login → Register |
| `nav_main.xml` | Grafo de navegación principal con los 5 fragments del BottomNavigation |
| `bottom_nav_menu.xml` | Ítems del menú inferior: Home, Horarios, Tareas, Notas, Asistencia |
| `ic_launcher.xml` | Ícono adaptativo de la app usando `ic_logo` como foreground |

---

## Convención de ramas sugerida

| Rama | Uso |
|------|-----|
| `main` | Código estable, listo para presentar |
| `develop` | Integración de features en desarrollo |
| `feature/horarios` | Nueva funcionalidad de horarios |
| `feature/tareas` | Nueva funcionalidad de tareas |
| `fix/nombre-bug` | Corrección de errores |

---

## Funcionalidades implementadas

| Feature | Descripción |
|---------|-------------|
| 🔐 Auth | Login/Registro con Firebase Auth |
| 📅 Horarios | CRUD de cursos con calendario semanal |
| ✅ Tareas | Gestión de tareas con prioridad y fechas |
| 📝 Notas | Bloc de notas organizado por curso |
| 📊 Asistencia | Control con % y alertas automáticas |
| 🔔 Notificaciones | Recordatorios de tareas y clases |
| 🌙 Dark mode | Soporte completo |
