<img width="1347" height="876" alt="Captura de pantalla 2026-06-11 095527" src="https://github.com/user-attachments/assets/21eeddf0-19c8-4042-ad4c-9fdcb52dbd82" />
# Diario Virtual

Aplicación móvil desarrollada para registrar y organizar recuerdos, notas y experiencias personales de forma sencilla y accesible.

---

# Descripción del problema

Muchas personas necesitan un espacio digital donde puedan guardar y organizar sus recuerdos, notas e ideas importantes. Sin embargo, no siempre cuentan con una herramienta simple y práctica para hacerlo desde su dispositivo móvil. Por ello surge **Diario Virtual**, una aplicación diseñada para facilitar el registro y consulta de información personal en cualquier momento.

---

## Funcionalidades implementadas

* Pantalla de inicio de sesión.
* Pantalla de registro de usuarios.
* Validación de campos obligatorios.
* Validación del formato del correo electrónico.
* Validación de longitud mínima de contraseña.
* Confirmación de contraseña en el registro.
* Mensajes de error junto a cada campo.
* Registro de usuarios en Room Database.
* Verificación de credenciales mediante correo y contraseña.
* Almacenamiento de la contraseña mediante hash SHA-256.
* Prevención de correos duplicados.
* Navegación desde el login hacia la pantalla principal.
* Limpieza del historial de navegación para evitar volver al login con el botón Atrás.

## Captura de la aplicación
<img width="494" height="788" alt="Captura de pantalla 2026-06-23 122500" src="https://github.com/user-attachments/assets/b9bcc846-dfb8-42a7-a7fa-106b74552061" />
Pantalla de inicio de sesión de la aplicación Diario Virtual funcionando en el emulador.

# Objetivo

Desarrollar una aplicación móvil que permita registrar, visualizar y organizar notas y recuerdos personales mediante una interfaz sencilla e intuitiva.

---``

# Historias de Usuario (MVP)

## Historia de Usuario 1

Como usuario, quiero ingresar a la aplicación para acceder a mi diario.

## Historia de Usuario 2

Como usuario, quiero visualizar una lista de notas para organizar mis recuerdos.

## Historia de Usuario 3

Como usuario, quiero ver el detalle de una nota para consultar su contenido.

---

# Framework Seleccionado

## Flutter

### Justificación

- Permite desarrollar aplicaciones móviles con interfaces modernas y atractivas.
- Facilita el desarrollo para Android e iOS utilizando un único código fuente.
- Posee una curva de aprendizaje amigable para principiantes.
- Cuenta con amplia documentación y soporte de la comunidad.

---

# Prototipo de la Aplicación

El prototipo fue diseñado en Figma y consta de tres pantallas principales:

### Pantalla de Bienvenida

Permite al usuario iniciar sesión y acceder a la aplicación.

### Pantalla de Inicio

Muestra las notas registradas por el usuario.

### Pantalla de Detalle

Permite visualizar la información completa de una nota y marcarla como completada.

<img width="1347" height="876" alt="Captura de pantalla 2026-06-11 095527" src="https://github.com/user-attachments/assets/76840074-8c41-4b84-9013-a1c4bf1b2fc9" />


---

# Capturas de la Aplicación

### Pantalla principal desarrollada en Android Studio

<img width="549" height="887" alt="Captura de pantalla 2026-06-11 100429" src="https://github.com/user-attachments/assets/a1e11621-e503-4a3a-9df8-80cf400a317b" />


---

# Instalación

## Requisitos

- Android Studio
- JDK 17 o superior
- Git
- Windows 10 o superior

## Pasos de instalación

1. Clonar el repositorio:

```bash
git clone https://github.com/MichaelLarrea/diario-virtual.git
```

2. Abrir el proyecto en Android Studio.

3. Esperar la sincronización de Gradle.

4. Ejecutar la aplicación en un emulador Android o dispositivo físico.

---

# Repositorio

Repositorio público del proyecto:

https://github.com/MichaelLarrea/diario-virtual

---

# Autor

Michael Larrea

Proyecto académico desarrollado para la asignatura de Desarrollo de Aplicaciones Móviles.

---

# Estado del Proyecto

Actualmente el proyecto se encuentra en fase inicial de desarrollo. Se ha implementado el diseño de la pantalla principal y la integración con GitHub para el control de versiones.

---

# Nuevas funcionalidades implementadas

* CRUD completo de notas: crear, visualizar, editar y eliminar.
* Almacenamiento local de notas mediante Room Database.
* Visualización de notas mediante RecyclerView.
* Formulario reutilizable para crear y editar notas.
* Confirmación antes de eliminar una nota.
* Opción para deshacer la eliminación mediante Snackbar.
* Integración de una API REST de frases mediante Retrofit.
* Conversión de respuestas JSON mediante Gson.
* Manejo de estados Loading, Success y Error con StateFlow.
* Implementación de notificaciones locales mediante WorkManager.
* Solicitud del permiso de notificaciones en Android 13 o superior.
* Recordatorios relacionados con los datos almacenados en Room.

---

# Arquitectura de datos

La aplicación utiliza una arquitectura organizada por capas para separar la interfaz, la lógica y el acceso a los datos.

```text
Activity
   ↓
ViewModel
   ↓
Repository
   ↓
DAO
   ↓
Room Database