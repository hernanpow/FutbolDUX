# API de Gestión de Equipos de Fútbol

API RESTful construida con Java y Spring Boot para gestionar información de equipos de fútbol. Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre una base de datos de equipos, con autenticación basada en JWT y una completa documentación interactiva a través de Swagger.

## Tabla de Contenidos
- [Características Principales](#características-principales)
- [Requisitos Técnicos](#requisitos-técnicos)
- [Requisitos Previos](#requisitos-previos)
- [Instalación y Ejecución](#instalación-y-ejecución)
  - [1. Clonar el Repositorio](#1-clonar-el-repositorio)
  - [2. Configurar la Aplicación](#2-configurar-la-aplicación)
  - [3. Ejecutar la Aplicación](#3-ejecutar-la-aplicación)
- [Uso de la API](#uso-de-la-api)
  - [Autenticación](#autenticación)
  - [Documentación Interactiva (Swagger)](#documentación-interactiva-swagger)
  - [Colección de Postman](#colección-de-postman)
- [Endpoints de la API](#endpoints-de-la-api)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Pruebas Unitarias](#pruebas-unitarias)

## Características Principales

✅ **Operaciones CRUD:** Gestión completa de equipos (crear, leer, actualizar, eliminar).
<br>
🔐 **Seguridad JWT:** Endpoints protegidos mediante JSON Web Tokens.
<br>
📘 **Documentación Interactiva:** API completamente documentada con Swagger (OpenAPI 3), permitiendo probar los endpoints directamente desde el navegador.
<br>
🗃️ **Base de Datos en Memoria:** Utiliza H2 Database para una configuración y ejecución rápidas y sencillas.
<br>
🚨 **Manejo de Excepciones:** Un manejador de excepciones global proporciona respuestas de error claras y consistentes en formato JSON.
<br>
🧪 **Pruebas Unitarias:** La lógica de negocio en la capa de servicio está cubierta por pruebas unitarias utilizando JUnit y Mockito.
<br>
🛠️ **Buenas Prácticas:** Desarrollado siguiendo principios SOLID, con una arquitectura de capas bien definida (Controlador, Servicio, Repositorio) y el uso de DTOs para el contrato de la API.

## Requisitos Técnicos

- **Lenguaje:** Java 17
- **Framework:** Spring Boot 3
- **Base de Datos:** H2 Database (en memoria)
- **Seguridad:** Spring Security con autenticación JWT
- **Documentación:** SpringDoc OpenAPI (Swagger)
- **Pruebas:** JUnit 5 y Mockito
- **Build Tool:** Maven

## Requisitos Previos

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) o superior.
- [Apache Maven](https://maven.apache.org/download.cgi) 3.6 o superior.
- [Git](https://git-scm.com/) para clonar el repositorio.
- Una herramienta para probar APIs como [Postman](https://www.postman.com/) o [Insomnia](https://insomnia.rest/).

## Instalación y Ejecución

### 1. Clonar el Repositorio

```bash
git clone https://github.com/tu-usuario/nombre-del-repositorio.git
cd nombre-del-repositorio
```

### 2. Configurar la Aplicación

El archivo de configuración principal se encuentra en `src/main/resources/application.properties`.

La aplicación viene pre-configurada para ejecutarse sin cambios. Sin embargo, es importante destacar la clave secreta de JWT:

```properties
# src/main/resources/application.properties

# Clave secreta para firmar los JWT.
# Para un entorno de producción, esta clave debe ser mucho más segura y gestionada externamente.
jwt.secret=TU_CLAVE_SECRETA_SUPER_LARGA_Y_ALEATORIA_EN_BASE64_AQUI
```

### 3. Ejecutar la Aplicación

Puedes ejecutar la aplicación utilizando el wrapper de Maven incluido:

```bash
# En Windows
./mvnw spring-boot:run

# En macOS/Linux
./mvnw spring-boot:run
```

Una vez iniciada, la aplicación estará disponible en `http://localhost:8080`.

## Uso de la API

### Autenticación

Todos los endpoints (excepto `/auth/login`) requieren un token JWT para ser accedidos.

1.  **Obtener el Token:** Realiza una petición `POST` a `/auth/login` con las siguientes credenciales de prueba:

    **Request:** `POST /auth/login`
    ```json
    {
      "username": "test",
      "password": "12345"
    }
    ```

    **Response:**
    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiJ9..."
    }
    ```

2.  **Usar el Token:** Incluye el token obtenido en la cabecera `Authorization` de tus peticiones a los endpoints protegidos.

    ```
    Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
    ```

### Documentación Interactiva (Swagger)

Una vez que la aplicación esté en ejecución, puedes acceder a la documentación interactiva de la API en tu navegador:

➡️ **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

Desde esta interfaz, podrás:
- Ver todos los endpoints disponibles.
- Consultar los modelos de datos (DTOs).
- Probar los endpoints directamente, incluyendo la autenticación JWT a través del botón "Authorize".

### Colección de Postman

Para facilitar las pruebas y la interacción con la API, se ha incluido una colección de Postman en el repositorio.

El archivo se encuentra en la siguiente ruta: `utils/DuxChallenge.postman_collection.json`

**Para utilizarla:**
1. Abre Postman.
2. Haz clic en `File > Import...` (o el botón `Import` en la barra lateral).
3. Selecciona el archivo mencionado para importar toda la colección de endpoints pre-configurados.

Se recomienda configurar un [entorno de Postman](https://learning.postman.com/docs/sending-requests/variables/managing-environments/) con una variable `baseUrl` con el valor `http://localhost:8080` para que las peticiones funcionen sin necesidad de cambios.

## Endpoints de la API

| Verbo HTTP | Endpoint                   | Descripción                                  |
|------------|----------------------------|----------------------------------------------|
| `POST`     | `/auth/login`              | Inicia sesión y obtiene un token JWT.        |
| `GET`      | `/equipos`                 | Obtiene la lista de todos los equipos.       |
| `GET`      | `/equipos/{id}`            | Obtiene un equipo por su ID.                 |
| `GET`      | `/equipos/buscar`          | Busca equipos por nombre (`?nombre=valor`).  |
| `POST`     | `/equipos`                 | Crea un nuevo equipo.                        |
| `PUT`      | `/equipos/{id}`            | Actualiza un equipo existente.               |
| `DELETE`   | `/equipos/{id}`            | Elimina un equipo por su ID.                 |

## Estructura del Proyecto

El proyecto sigue una arquitectura de capas estándar para aplicaciones Spring Boot:

- `config`: Clases de configuración de Spring (Seguridad, Swagger, etc.).
- `controller`: Controladores REST que manejan las peticiones HTTP.
- `dto`: Data Transfer Objects (DTOs) que definen el contrato de la API.
- `entity`: Entidades JPA que se mapean a las tablas de la base de datos.
- `exception`: Manejador de excepciones global y excepciones personalizadas.
- `mapper`: Clases de utilidad para convertir entre Entidades y DTOs.
- `repository`: Interfaces de Spring Data JPA para el acceso a datos.
- `security`: Componentes relacionados con JWT (servicio, filtro).
- `service`: Lógica de negocio de la aplicación.

## Pruebas Unitarias

El proyecto incluye una suite de pruebas unitarias para la capa de servicio (`EquipoServiceImpl`). Para ejecutar las pruebas, utiliza el siguiente comando de Maven:

``bash
./mvnw test``

Estas pruebas utilizan **Mockito** para simular la capa de repositorio, asegurando que la lógica de negocio se prueba de forma aislada.
