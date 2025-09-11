# 🤖 Sistema de Evaluaciones y Entrevistas Automatizadas con IA

Este proyecto tiene como objetivo desarrollar un **sistema de entrevistas y evaluaciones automatizadas** que integra **inteligencia artificial, grabación de audio/video y almacenamiento en la nube (AWS)**.  
El sistema permite realizar entrevistas estructuradas, almacenar respuestas y generar evaluaciones en base a criterios predefinidos.

---

## 📌 Requisitos previos

Antes de ejecutar el proyecto asegúrate de contar con lo siguiente:

### 🔹 General
- [Git](https://git-scm.com/) instalado
- [Java 17+](https://adoptium.net/) (para backend con Spring Boot)
- [Maven 3.8+](https://maven.apache.org/) o [Gradle](https://gradle.org/) según tu configuración
- [Node.js 18+](https://nodejs.org/) y [npm](https://www.npmjs.com/) o [yarn](https://yarnpkg.com/) (para frontend)

### 🔹 Frontend
- [React 18 con Vite](https://vitejs.dev/)
- [TailwindCSS](https://tailwindcss.com/) (estilos)
- [AWS SDK for JavaScript v3](https://docs.aws.amazon.com/AWSJavaScriptSDK/v3/latest/) (para conexión con Chime, S3, Polly)

### 🔹 Backend
- **Spring Boot 3+** con Clean Architecture
- **Spring Cloud** (para comunicación entre microservicios)
- **AWS SDK v2/v3 para Java**
- **Micrometer Tracing + Zipkin** (para trazabilidad distribuida)

### 🔹 Multimedia
- [FFmpeg](https://ffmpeg.org/) instalado en el sistema (para procesar audio/video)
    - Windows: `C:/ffmpeg/bin/ffmpeg.exe`
    - Linux/Mac: `/usr/bin/ffmpeg` o simplemente `ffmpeg` si está en PATH
    - Antes ahora ya no

### 🔹 Usar la api de openai
- Crear una cuenta en [OpenAI](https://platform.openai.com/signup)

### 🔹 AWS
- **Amazon S3** → almacenamiento de grabaciones
- **Amazon Polly** → conversión de texto a voz
- **AWS IAM** → credenciales y políticas de seguridad

---

## 🚀 Instalación

### 🔹 Backend (Spring Boot)
```bash
git clone https://github.com/tu-usuario/tu-repo.git
cd backend
mvn clean install
mvn spring-boot:run
