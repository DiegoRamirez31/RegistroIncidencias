# RegistroIncidencias

Aplicación Android desarrollada para el registro y seguimiento de incidencias.

## Herramientas utilizadas

- Android Studio
- Kotlin
- Jetpack Compose
- Git
- GitHub

## Estado actual

Actualmente, la aplicación cuenta con una interfaz para el registro de incidencias. La pantalla permite ingresar el título y la descripción de una incidencia mediante campos de texto.

Se implementó estado básico con remember y mutableStateOf para conservar la información ingresada por el usuario. Al presionar el botón "Registrar Incidencia", la aplicación muestra una retroalimentación visible con el título del reporte preparado.

La funcionalidad para almacenar las incidencias en una base de datos aún no ha sido implementada.


## Avance Semana 6

Se incorporó interacción básica y manejo de estado en la interfaz utilizando remember y mutableStateOf. Los campos permiten ingresar y conservar el título y la descripción de la incidencia.

El botón "Registrar Incidencia" actualiza el estado de la aplicación y muestra una retroalimentación visible con el título del reporte preparado. La funcionalidad fue comprobada mediante la ejecución de la aplicación en un dispositivo físico.

## Avance Semana 7

Se incorporó validación básica en el formulario de registro de incidencias. Al presionar el botón "Registrar Incidencia", la aplicación verifica que los campos de título y descripción contengan información.

Si alguno de los campos está vacío, se muestra el mensaje "Completa los campos" en color rojo y negrita para orientar al usuario. Cuando ambos campos contienen información, la aplicación muestra la retroalimentación correspondiente con el título del reporte preparado.

La validación fue comprobada mediante la ejecución de la aplicación en un dispositivo Android físico.

## Cómo abrir el proyecto

1. Descargar o clonar el repositorio.
2. Abrir Android Studio.
3. Seleccionar la opción para abrir un proyecto existente.
4. Seleccionar la carpeta del proyecto RegistroIncidencias.
5. Esperar a que Gradle sincronice el proyecto.
6. Ejecutar la aplicación en un emulador o dispositivo Android.

## Autor

Diego Emmanuel Chavez Ramírez
Técnicas de Producción Industrial de Software I  
Universidad Tecnológica de El Salvador