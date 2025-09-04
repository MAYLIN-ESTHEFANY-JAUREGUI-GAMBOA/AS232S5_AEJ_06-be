# Documentación de la API - Detección de Edad con Spring Boot

## ✅ **APIs IA y Características**

### 1. **Age Detector (RapidAPI)**  
**Descripción**:  
El **Age Detector** es un servicio de **RapidAPI** que utiliza modelos avanzados de visión artificial para detectar los rostros en una imagen y estimar la edad de cada persona basándose en sus atributos faciales. Devuelve una lista con los rostros identificados, su edad estimada y la probabilidad de esa estimación.

**Características**:
- Detecta todos los rostros en una imagen.
- Estima la edad de cada rostro detectado utilizando atributos faciales.
- Devuelve los resultados con la edad estimada y las probabilidades asociadas.

### 2. **YouTube MP3 (RapidAPI)**  
**Descripción**:  
La API **YouTube MP3** permite convertir videos de YouTube en archivos de audio MP3. Esta API permite descargar directamente archivos MP3 de los videos de YouTube.

**Características**:
- Convierte videos de YouTube a formato MP3.
- Requiere una clave de API para autenticación y acceso.
- Proporciona un enlace de descarga directa para el archivo MP3.

**Enlace**:  
- [Convertir YouTube a MP3](https://youtubedownload.minitool.com/youtube/upload-mp3-to-youtube.html)

### 3. **Instagram API (RapidAPI)**  
**Descripción**:  
La **Instagram API** de RapidAPI permite acceder a datos públicos de Instagram, como publicaciones, fotos, seguidores y más. Facilita la interacción programática con la plataforma Instagram.

**Características**:
- Acceso a datos públicos de Instagram, incluidas publicaciones, fotos y seguidores.
- Permite interactuar con las cuentas de Instagram de manera automatizada.
- Requiere una clave de API para la autenticación.

**Enlace**:  
- [Instagram en Getty Images](https://www.gettyimages.com/photos/instagram)

## ✅ **Herramientas y Versiones Utilizadas**

### **Entorno de Desarrollo**:
- **Java**: JDK 17
- **Framework**: Spring Boot 3.5.5
- **Base de Datos**: PostgreSQL (a través de R2DBC)
- **Gestor de Dependencias**: Maven
- **Entorno de Desarrollo**: IntelliJ IDEA, Visual Studio Code, Codespace

### **Dependencias Principales**:
- **Spring WebFlux**: Para desarrollar aplicaciones reactivas y escalables.
- **Spring Data R2DBC**: Para la integración con bases de datos reactivas como PostgreSQL.
- **Swagger UI**: Para la documentación interactiva de la API.
- **Lombok**: Para reducir la verbosidad del código en Java.
- **Maven**: Para gestionar dependencias y construir el proyecto.

## ✅ **Configuración del Proyecto**

### Configuración Completa del `application.yml`:
El archivo `application.yml` es utilizado para gestionar la configuración del servidor, la base de datos y las claves de API. A continuación se muestra una configuración detallada del archivo.

```yaml
server:
  port: ${PORT:8085}  # Valor por defecto si PORT no está configurado
  url: ${SERVER_URL:https://fuzzy-broccoli-wp475v45r5j394p7-8085.app.github.dev}  # Valor por defecto si SERVER_URL no está configurado

spring:
  application:
    name: API-AI
  r2dbc:
    url: r2dbc:postgresql://neondb_owner:npg_VLw14dKbnlIx@ep-withered-bird-aca8elbd-pooler.sa-east-1.aws.neon.tech/apiai?sslmode=require&channel_binding=require
    username: neondb_owner
    password: npg_VLw14dKbnlIx
  # Configuración de logging
logging:
  level:
    org.springframework.r2dbc: DEBUG  # Nivel de log para R2DBC (base de datos reactiva)
    org.springframework.web.reactive.function.client: DEBUG  # Nivel de log para las solicitudes de cliente WebFlux

# Configuración de RapidAPI para la integración con YouTube y Instagram
rapidapi:
  youtube-mp36:
    url: https://youtube-mp36.p.rapidapi.com
    host: youtube-mp36.p.rapidapi.com
    apikey: 11d5a59a70msh928a6b51895e702p12ab87jsn7b4f24c628ba  # Clave API para YouTube MP3

  instagram120:
    url: https://instagram120.p.rapidapi.com
    host: instagram120.p.rapidapi.com
    apikey: 11d5a59a70msh928a6b51895e702p12ab87jsn7b4f24c628ba  # Clave API para Instagram

# Configuración de Swagger UI para generar documentación interactiva de la API
springdoc:
  api-docs:
    enabled: true
    path: /api-docs  # Endpoint para obtener la documentación en formato JSON
  swagger-ui:
    enabled: true
    path: /swagger-ui.html  # URL para acceder a la UI de Swagger
