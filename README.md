# MicroserviceTechnician

## Descripción

Este proyecto es un microservicio que maneja información relacionada con técnicos y servicios. Utiliza **Spring Boot** para el backend y una base de datos **PostgreSQL** para almacenar la información.

## Prerrequisitos

- **Java 17** o superior.
- **Gradle** 
- **Docker** y **Docker Compose** para ejecutar la base de datos en contenedores.
- **IntelliJ IDEA** (u otro IDE compatible con proyectos de Java).

## Configuración del Proyecto

### 1. Clonar el repositorio

Clona el repositorio desde GitHub y accede al directorio del proyecto:

```bash
https://github.com/BACKEND-JJOS/MicroserviceTechnician.git
cd MicroserviceTechnician
git checkout develop
git pull origin develop
```

### 2. Configuración de la base de datos con Docker Compose

Este proyecto utiliza una base de datos PostgreSQL que puedes configurar rápidamente utilizando Docker. Para levantar la base de datos, simplemente ejecuta el siguiente comando:

#### Nota: Recuerde tener actualizado docker

```bash
docker-compose up -d
```

Esto levantará un contenedor de PostgreSQL con las siguientes configuraciones predeterminadas:

- Usuario: postgres
- Contraseña: postgres
- Puerto local: 5432

El contenedor almacenará los datos de forma persistente en un volumen de Docker llamado postgres_data.

### 3. Ejecutar el proyecto en IntelliJ

1. Abre IntelliJ IDEA y selecciona File > Open para cargar el proyecto.
2. Asegúrate de que todas las dependencias estén correctamente instaladas .
3. Ejecuta la clase principal del proyecto MainApplication.

Una vez ejecute el proyecto puede hacer uso de los endpoint, recuerde que si desea ver la documentación swagger puede entra a 

    http://localhost:8020/api/doc/swagger-ui/webjars/swagger-ui/index.html


# Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## Domain

Es el módulo mas interno de la arquitectura, pertenece a la capa del dominio y encapsula la lógica y reglas del negocio mediante modelos y entidades del dominio.

## Usecases

Este módulo gradle perteneciente a la capa del dominio, implementa los casos de uso del sistema, define lógica de aplicaci�n y reacciona a las invocaciones desde el m�dulo de entry points, orquestando los flujos hacia el m�dulo de entities.

## Dependencias por Paquete


| **Paquete**                    | **Dependencias**                                                                                                 |
|---------------------------------|------------------------------------------------------------------------------------------------------------------|
| **`domain`**                    |                                                                                                                  |
|                                 | `org.springframework.boot:spring-boot-starter`                                                                    |
|                                 | `org.reactivecommons.utils:object-mapper:0.1.0`                                                                  |
|                                 | `com.google.code.gson:gson:2.11.0`                                                                                |
|                                 | `com.fatboyindustrial.gson-javatime-serialisers:gson-javatime-serialisers:1.1.2`                                  |
|                                 | `com.tngtech.archunit:archunit:1.3.0`                                                                            |
|                                 | `com.fasterxml.jackson.core:jackson-databind`                                                                     |
|                                 | **Proyectos locales**                                                                                            |
|                                 | `:r2dbc-postgresql`                                                                                              |
|                                 | `:mongo-repository`                                                                                               |
|                                 | `:reactive-web`                                                                                                  |
|                                 | `:model`                                                                                                         |
|                                 | `:usecase`                                                                                                       |
|                                 | **Runtime**                                                                                                      |
|                                 | `org.springframework.boot:spring-boot-devtools`                                                                  |
| **`infrastructure.driven-adapter`** |                                                                                                              |
|                                 | `org.springframework:spring-context`                                                                             |
|                                 | `org.springframework.boot:spring-boot-starter-data-r2dbc`                                                        |
|                                 | `jakarta.persistence:jakarta.persistence-api` (pendiente de revisión)                                             |
|                                 | `org.postgresql:r2dbc-postgresql`                                                                                |
|                                 | `org.reactivecommons.utils:object-mapper-api:0.1.0`                                                              |
|                                 | `com.google.code.gson:gson:2.11.0`                                                                                |
|                                 | `com.fatboyindustrial.gson-javatime-serialisers:gson-javatime-serialisers:1.1.2`                                  |
|                                 | **Proyectos locales**                                                                                            |
|                                 | `:model`                                                                                                         |
|                                 | **Test**                                                                                                         |
|                                 | `org.reactivecommons.utils:object-mapper:0.1.0`                                                                  |
| **`infrastructure.entry-points`**|                                                                                                                  |
|                                 | `org.springframework.boot:spring-boot-starter-webflux`                                                           |
|                                 | `org.springframework.boot:spring-boot-starter-actuator`                                                          |
|                                 | `org.springframework.boot:spring-boot-starter-validation:3.3.4`                                                  |
|                                 | `io.micrometer:micrometer-registry-prometheus`                                                                   |
|                                 | `org.springdoc:springdoc-openapi-starter-webflux-ui:2.6.0`                                                       |
|                                 | `com.google.code.gson:gson:2.11.0`                                                                                |
|                                 | `com.fatboyindustrial.gson-javatime-serialisers:gson-javatime-serialisers:1.1.2`                                  |
|                                 | **Proyectos locales**                                                                                            |
|                                 | `:usecase`                                                                                                       |
|                                 | `:model`                                                                                                         |


## Infrastructure

### Helpers

En el apartado de helpers tendremos utilidades generales para los Driven Adapters y Entry Points.

Estas utilidades no est�n arraigadas a objetos concretos, se realiza el uso de generics para modelar comportamientos
genéricos de los diferentes objetos de persistencia que puedan existir, este tipo de implementaciones se realizan
basadas en el patr�n de dise�o [Unit of Work y Repository](https://medium.com/@krzychukosobudzki/repository-design-pattern-bc490b256006)

Estas clases no puede existir solas y debe heredarse su compartimiento en los **Driven Adapters**

### Driven Adapters

Los driven adapter representan implementaciones externas a nuestro sistema, como lo son conexiones a servicios rest,
soap, bases de datos, lectura de archivos planos, y en concreto cualquier origen y fuente de datos con la que debamos
interactuar.

### Entry Points

Los entry points representan los puntos de entrada de la aplicaci�n o el inicio de los flujos de negocio.

## Application

Este módulo es el más externo de la arquitectura, es el encargado de ensamblar los distintos módulos, resolver las dependencias y crear los beans de los casos de use (UseCases) de forma autom�tica, inyectando en �stos instancias concretas de las dependencias declaradas. Adem�s inicia la aplicaci�n (es el único módulo del proyecto donde encontraremos la función public static void main(String[] args).

**Los beans de los casos de uso se disponibilizan automaticamente gracias a un '@ComponentScan' ubicado en esta capa.**


