# StockGain - Plataforma de Inversión Responsable

## 📊 Descripción del Proyecto

StockGain es una aplicación web completa para inversión responsable que integra análisis financiero tradicional con criterios ESG (Environmental, Social, Governance). La plataforma permite a los usuarios gestionar sus portafolios de inversión alineándolos con sus valores personales y responsabilidad social.

## 🏗️ Arquitectura del Sistema

### Frontend
- **HTML5**: Estructura semántica y accesible
- **CSS3**: Estilos personalizados con animaciones y efectos visuales
- **JavaScript ES6+**: Funcionalidades interactivas y comunicación con APIs
- **TailwindCSS**: Framework CSS utility-first para el dashboard
- **ApexCharts**: Librería de gráficos interactivos

### Backend
- **Spring Boot 3.5.3**: Framework principal del backend
- **Spring Security**: Autenticación y autorización con JWT
- **Spring Data JPA**: Persistencia de datos con Hibernate
- **MySQL**: Base de datos relacional principal
- **H2 Database**: Base de datos en memoria para desarrollo
- **Maven**: Gestión de dependencias y construcción

### APIs Externas
- **Alpha Vantage API**: Datos de precios de mercado en tiempo real

## 📁 Estructura del Proyecto

```
proyecto_final/
├── 📂 api/                          # Backend Spring Boot
│   ├── 📂 src/main/java/com/stockgain/api/
│   │   ├── 📄 ApiApplication.java   # Clase principal Spring Boot
│   │   ├── 📂 config/              # Configuración JWT y Seguridad
│   │   │   ├── 📄 JwtUtil.java     # Utilidades JWT
│   │   │   ├── 📄 JwtRequestFilter.java # Filtro de autenticación
│   │   │   └── 📄 SecurityConfig.java   # Configuración seguridad
│   │   ├── 📂 controller/          # Controladores REST
│   │   │   ├── 📄 AuthController.java      # Autenticación
│   │   │   ├── 📄 PortfolioController.java # Gestión portafolios
│   │   │   └── 📄 UserController.java      # Gestión usuarios
│   │   ├── 📂 model/              # Modelos JPA
│   │   │   ├── 📄 Usuario.java     # Modelo Usuario
│   │   │   ├── 📄 Activo.java      # Modelo Activo
│   │   │   ├── 📄 Portafolio.java  # Modelo Portafolio
│   │   │   └── 📄 Posicion.java    # Modelo Posición
│   │   ├── 📂 repository/         # Repositorios JPA
│   │   └── 📂 service/            # Servicios de negocio
│   ├── 📂 src/main/resources/
│   │   ├── 📄 application.properties      # Configuración principal
│   │   └── 📄 application-h2.properties  # Configuración H2
│   ├── 📄 pom.xml                 # Configuración Maven
│   └── 📄 HELP.md                 # Documentación Spring Boot
├── 📂 css/                        # Estilos CSS
│   ├── 📄 acceso_styles.css       # Estilos autenticación
│   ├── 📄 dashboard_styles.css    # Estilos dashboard
│   └── 📄 landing_page_styles.css # Estilos página principal
├── 📂 html/                       # Páginas HTML
│   ├── 📄 acceso.html            # Página login/registro
│   ├── 📄 dashboard.html         # Dashboard principal
│   └── 📄 landing_page.html      # Página de inicio
├── 📂 javascript/                 # Funcionalidades JavaScript
│   ├── 📄 auth_functions.js       # Funciones autenticación
│   ├── 📄 dashboard_functions.js  # Funciones dashboard
│   └── 📄 landing_page_functions.js # Funciones landing page
├── 📂 img/                       # Recursos visuales
│   └── [logos, iconos, imágenes de fondo]
└── 📄 README.md                  # Documentación principal
```

## ✨ Características Principales

### 🔐 Autenticación y Seguridad
- ✅ Sistema de registro y login con validación
- ✅ Autenticación JWT stateless
- ✅ Cifrado de contraseñas con BCrypt
- ✅ Protección CSRF y configuración CORS
- ✅ Filtros de seguridad personalizados

### 📈 Gestión de Portafolios
- ✅ Visualización de valor total del portafolio
- ✅ Tabla interactiva de activos
- ✅ Operaciones de compra y venta
- ✅ Análisis de rendimiento
- ✅ Cálculo de precio promedio ponderado

### 🌱 Análisis ESG
- ✅ Calificación ESG de activos (A, B, C)
- ✅ Visualización de distribución ESG
- ✅ Filtrado por criterios de sostenibilidad
- ✅ Impacto social y ambiental

### 📊 Datos en Tiempo Real
- ✅ Integración con Alpha Vantage API
- ✅ Precios de mercado actualizados
- ✅ Gráficos de evolución temporal
- ✅ Notificaciones de cambios significativos

### 💻 Interfaz de Usuario
- ✅ Diseño responsive para múltiples dispositivos
- ✅ Animaciones suaves y efectos visuales
- ✅ Navegación intuitiva
- ✅ Gráficos interactivos con ApexCharts

## 🚀 Instalación y Configuración

### 📋 Requisitos Previos
- ☕ Java 24 o superior
- 📦 Maven 3.6 o superior
- 🐬 MySQL 8.0 o superior
- 🌐 Navegador web moderno
- 🔧 IDE (recomendado: IntelliJ IDEA o VS Code)

### ⚙️ Configuración del Backend

1. **📥 Clonar el repositorio**
```bash
git clone [URL_DEL_REPOSITORIO]
cd proyecto_final
```

2. **🗄️ Configurar base de datos MySQL**
```sql
-- Crear base de datos
CREATE DATABASE stockgain_db;

-- Crear usuario específico (opcional)
CREATE USER 'stockgain'@'localhost' IDENTIFIED BY 'Tecnicas123';
GRANT ALL PRIVILEGES ON stockgain_db.* TO 'stockgain'@'localhost';
FLUSH PRIVILEGES;
```

3. **🔧 Configurar application.properties**
```properties
# Configuración de base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/stockgain_db
spring.datasource.username=root
spring.datasource.password=Tecnicas123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuración JPA
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

4. **🏃‍♂️ Ejecutar la aplicación**
```bash
cd api
mvn clean install
mvn spring-boot:run
```

### 🌐 Configuración del Frontend

1. **📂 Servir archivos estáticos**
   - Usar Live Server en VS Code
   - O cualquier servidor web local en el puerto 5500

2. **🔑 Configurar API Key de Alpha Vantage**
   - Obtener clave gratuita en https://www.alphavantage.co/support/#api-key
   - Actualizar la constante `ALPHA_VANTAGE_API_KEY` en `dashboard_functions.js`

3. **🌍 Acceder a la aplicación**
   - Abrir http://localhost:5500/html/landing_page.html

## 🎯 Uso de la Aplicación

### 🏠 Página de Inicio
- Navegación fluida entre secciones
- Información sobre características ESG
- Acceso a registro/login

### 🔐 Autenticación
- Registro de nuevos usuarios
- Inicio de sesión con validación
- Recuperación de sesión automática

### 📊 Dashboard Principal
- Resumen del portafolio con gráficos
- Tabla de activos con opciones de venta
- Formulario para añadir nuevos activos
- Análisis ESG visual

### ⚙️ Configuración de Perfil
- Actualización de datos personales
- Cambio de contraseña
- Configuración de preferencias

## 🔗 API Endpoints

### 🔐 Autenticación
```
POST /api/auth/registro    # Registro de usuario
POST /api/auth/login       # Inicio de sesión
```

### 📈 Portafolio
```
GET    /api/portfolio/{userId}           # Obtener portafolio
POST   /api/portfolio/add                # Añadir posición
DELETE /api/portfolio/sell/{posicionId}  # Vender posición
```

### 👤 Usuario
```
PUT /api/user/{userId}           # Actualizar perfil
PUT /api/user/{userId}/password  # Cambiar contraseña
```

## 🛠️ Tecnologías y Dependencias

### 🔧 Backend
- **Spring Boot 3.5.3**: Framework principal
- **Spring Security 6.0**: Seguridad y autenticación
- **Spring Data JPA**: Persistencia de datos
- **MySQL Connector**: Driver de base de datos
- **JJWT**: JSON Web Tokens
- **H2 Database**: Base de datos de desarrollo
- **BCrypt**: Cifrado de contraseñas

### 🎨 Frontend
- **HTML5, CSS3, JavaScript ES6+**: Tecnologías base
- **TailwindCSS 3.0**: Framework CSS utility-first
- **ApexCharts 3.0**: Gráficos interactivos
- **BoxIcons 2.1.4**: Iconos vectoriales
- **Font Awesome 5.15.4**: Iconos adicionales
- **Google Fonts**: Tipografías (Montserrat, Poppins)

## 🎨 Paleta de Colores

- **#222831**: 🌑 Color de fondo principal (oscuro)
- **#393E46**: 🌒 Color de fondo secundario
- **#00ADB5**: 🌊 Color de destacado (aqua/teal)
- **#EEEEEE**: ⚪ Color de texto principal (claro)

## 📁 Archivos Documentados

### ✅ JavaScript
- `auth_functions.js` - Funciones de autenticación
- `dashboard_functions.js` - Funcionalidades del dashboard
- `landing_page_functions.js` - Animaciones de la landing page

### ✅ HTML
- `landing_page.html` - Página de inicio
- `acceso.html` - Página de login/registro
- `dashboard.html` - Dashboard principal

### ✅ CSS
- `landing_page_styles.css` - Estilos página principal
- `acceso_styles.css` - Estilos de autenticación
- `dashboard_styles.css` - Estilos del dashboard

### ✅ Java Backend
- `ApiApplication.java` - Clase principal Spring Boot
- `SecurityConfig.java` - Configuración de seguridad
- `JwtUtil.java` - Utilidades JWT
- `JwtRequestFilter.java` - Filtro de autenticación
- `AuthController.java` - Controlador de autenticación
- `PortfolioController.java` - Controlador de portafolios
- `UserController.java` - Controlador de usuarios
- `Usuario.java` - Modelo de usuario
- `Activo.java` - Modelo de activo

### ✅ Configuración
- `pom.xml` - Configuración Maven
- `application.properties` - Configuración de aplicación
- `HELP.md` - Documentación de referencia

## 🔮 Mejoras Futuras

- 🔄 Integración con más APIs financieras
- 🤖 Análisis predictivo con Machine Learning
- 📱 Notificaciones push
- 📲 Aplicación móvil
- 📊 Análisis ESG más detallado
- 🌐 Soporte multi-idioma
- 📧 Notificaciones por email
- 📈 Alertas de precios personalizadas

## 📝 Licencia

Este proyecto está desarrollado como proyecto final para el curso de **Técnicas de Programación**.

## 👥 Equipo de Desarrollo

**StockGain Team**
- 📧 Email: [angel.roman@udea.edu.co]
- 💻 GitHub: [https://github.com/AngelUdea/StockGain/]

---

*✨ Desarrollado con 💚 para promover la inversión responsable y sostenible*
