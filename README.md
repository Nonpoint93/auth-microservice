# auth-microservice

Este proyecto es un microservicio de autenticación desarrollado con Spring Boot, empaquetado en una imagen Docker y configurado para funcionar junto con un servicio externo de autenticación (`auth-vivelibre`). Proporciona un endpoint que devuelve un token y un timestamp.

## **Requisitos previos**

Asegúrate de tener instalados los siguientes componentes en tu máquina:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Git](https://git-scm.com/)

## **Cómo clonar el repositorio**

Clona este repositorio para obtener el código fuente:

```bash
git clone https://github.com/nonpoint93/auth-microservice.git
cd auth-microservice
```

## Construcción y despliegue con Docker

1. Construir la imagen Docker del microservicio:
```
docker build -t nonpoint93/auth-microservice:latest .
```
2. Crear una red Docker para los servicios:
```
docker network create auth-network
```
3. Levantar los contenedores con Docker Compose:
```
docker compose up -d
```

(Este comando hará lo siguiente

Descargar e iniciar el servicio auth-vivelibre desde Docker Hub.

Iniciar el contenedor del microservicio auth-microservice.

Conectar ambos contenedores a la red auth-network.)

4. Verificar que los servicios están corriendo:
```
docker ps
```
Deberías ver los contenedores auth-vivelibre y auth-microservice en la lista.

## Prueba del microservicio

curl http://localhost:8081/api/v1/token

Response:

{
"token": "your-generated-token",
"timestamp": "2025-03-13T14:00:00Z"
}

{
"timestamp":"2025-03-13T03:55:44.460989838Z",
"token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhdXRoLXZpdmVsaWJyZSIsImV4cCI6MTc0MTg1NjE0NCwiaWF0IjoxNzQxODM4MTQ0fQ.yF7ywlww9ywadr_v4Yc1o1fIJdDohfSLelZkkVolIRowJI5WzwRwRJms_FaNC-oIfzfBtSQDEn-VgKwZRuvN6Q"
}

## Para detener y eliminar los contenedores:

```
docker stop auth-microservice auth-vivelibre
docker rm auth-microservice auth-vivelibre
```

## Descripción del docker-compose.yml

El archivo docker-compose.yml facilita la ejecución de los servicios en contenedores Docker. Este archivo define lo siguiente:

    El contenedor auth-microservice, que utiliza la imagen nonpoint93/auth-microservice:latest y está mapeado al puerto 8081.

    El contenedor auth-vivelibre, que utiliza la imagen skeet15/auth-vivelibre y está mapeado al puerto 8080.

    La red Docker personalizada auth-network, que permite la comunicación entre ambos servicios.

