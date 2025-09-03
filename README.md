# Implementacion del microservicio AUTENTICACION

## Antes de Iniciar

Empezaremos por explicar los diferentes componentes del proyectos y partiremos de los componentes externos, continuando con los componentes core de negocio (dominio) y por �ltimo el inicio y configuraci�n de la aplicaci�n.

Lee el art�culo [Clean Architecture � Aislando los detalles](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a)

# Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## Domain

Es el m�dulo m�s interno de la arquitectura, pertenece a la capa del dominio y encapsula la l�gica y reglas del negocio mediante modelos y entidades del dominio.

## Usecases

Este m�dulo gradle perteneciente a la capa del dominio, implementa los casos de uso del sistema, define l�gica de aplicaci�n y reacciona a las invocaciones desde el m�dulo de entry points, orquestando los flujos hacia el m�dulo de entities.

## Infrastructure

### Helpers

En el apartado de helpers tendremos utilidades generales para los Driven Adapters y Entry Points.

Estas utilidades no est�n arraigadas a objetos concretos, se realiza el uso de generics para modelar comportamientos
gen�ricos de los diferentes objetos de persistencia que puedan existir, este tipo de implementaciones se realizan
basadas en el patr�n de dise�o [Unit of Work y Repository](https://medium.com/@krzychukosobudzki/repository-design-pattern-bc490b256006)

Estas clases no puede existir solas y debe heredarse su compartimiento en los **Driven Adapters**

### Driven Adapters

Los driven adapter representan implementaciones externas a nuestro sistema, como lo son conexiones a servicios rest,
soap, bases de datos, lectura de archivos planos, y en concreto cualquier origen y fuente de datos con la que debamos
interactuar.

### Entry Points

Los entry points representan los puntos de entrada de la aplicaci�n o el inicio de los flujos de negocio.

## Application

Este m�dulo es el m�s externo de la arquitectura, es el encargado de ensamblar los distintos m�dulos, resolver las dependencias y crear los beans de los casos de use (UseCases) de forma autom�tica, inyectando en �stos instancias concretas de las dependencias declaradas. Adem�s inicia la aplicaci�n (es el �nico m�dulo del proyecto donde encontraremos la funci�n �public static void main(String[] args)�.

**Los beans de los casos de uso se disponibilizan automaticamente gracias a un '@ComponentScan' ubicado en esta capa.**

# Ejecutar la aplicación
`Java 21`

Seguir los siguientes pasos para levantar el avance de la aplicación.   

## Github

El avance se encuentra en la rama **feature/hu01-user** del repositorio: https://github.com/renzoandre/CYAutenticacion.

La url exacta es: https://github.com/renzoandre/CYAutenticacion/tree/feature/hu01-user#

## Credenciales de BD

Las credenciales aún estan en duro en el archivo **application.yaml**.

## Levantar docker
Existe el archivo **docker-compose.yml** con la configuración para tener la base de datos en un contendor docker.

EL archivo se encuentra en la carpeta **/database** donde se ubicará para ejecutar el siguiente comando.

```
docker-compose up -d
```

## Probar con Postman
Se tiene implementado 3 end point:

### Crear nuevo usuario
Se usará la siguiente peticion POST: http://localhost:8080/api/v1/user

```
{
    "name": "Felipe",
    "lastName1": "Lozada",
    "lastName2": "Lozano",
    "birthDate": "2010-02-01",
    "address": "Direccion de felipe",
    "phone": "111222333",
    "email": "felipe@email.com",
    "baseSalary": 10000
}
```
### Listar todos los usuarios
Se usará la siguiente peticion GET: http://localhost:8080/api/v1/users

### Actualizar usuario
Se usará la siguiente peticion PATCH: http://localhost:8080/api/v1/user

```
{
    "id": "uuid generado anteriormente",
    "name": "Felipe Modificado",
    "lastName1": "Lozada",
    "lastName2": "Lozano",
    "birthDate": "2010-02-01",
    "address": "Direccion de felipe",
    "phone": "111222333",
    "email": "felipe@email.com",
    "baseSalary": 10000
}
```