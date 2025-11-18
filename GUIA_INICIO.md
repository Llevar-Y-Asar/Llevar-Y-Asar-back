# Backend Llevar y Asar - Guía de Inicio Rápido

## Configuración Inicial

### 1. **Instalación de Dependencias**
El proyecto ya tiene todas las dependencias en `pom.xml`:
- Spring Boot 3.5.7
- MongoDB
- Swagger (SpringDoc OpenAPI)
- Security, Validation, Web

### 2. **Configurar MongoDB**

#### Opción A: MongoDB Local
1. Instala MongoDB desde [https://www.mongodb.com/try/download/community](https://www.mongodb.com/try/download/community)
2. Inicia el servicio:
```powershell
# Windows
net start MongoDB
# O ejecuta mongod.exe directamente
```

#### Opción B: MongoDB Atlas (Nube)
1. Crea una cuenta en [MongoDB Atlas](https://www.mongodb.com/cloud/atlas)
2. Crea un cluster
3. Obtén tu connection string
4. Actualiza `application.properties`:
```properties
spring.data.mongodb.uri=mongodb+srv://usuario:contraseña@cluster.xxxxx.mongodb.net/llevarayasar
```

### 3. **Ejecutar la Aplicación**
```powershell
cd C:\Users\pablo\Desktop\Llevar-Y-Asar-back
.\mvnw spring-boot:run
```

La aplicación estará disponible en: **http://localhost:8080**

---

## Acceso a Swagger (Documentación Interactiva)

Una vez que la aplicación esté ejecutándose, accede a:

**📚 Swagger UI:** http://localhost:8080/swagger-ui.html

**📄 API Docs (JSON):** http://localhost:8080/v3/api-docs

---

## Endpoints Principales

### **Productos**
- `GET /api/productos` - Obtener productos activos
- `GET /api/productos/{id}` - Obtener detalle de producto
- `GET /api/productos/categoria/{categoria}` - Filtrar por categoría
- `GET /api/productos/buscar?nombre=X` - Buscar productos
- `POST /api/productos/admin` - Crear producto (Admin)
- `PUT /api/productos/admin/{id}` - Actualizar producto (Admin)
- `DELETE /api/productos/admin/{id}` - Eliminar producto (Admin)

### **Usuarios (con RUT chileno)**
- `POST /api/usuarios/registro` - Registrar nuevo usuario
- `POST /api/usuarios/login` - Iniciar sesión (RUT + contraseña)
- `GET /api/usuarios/{rut}` - Obtener perfil por RUT
- `PUT /api/usuarios/{rut}` - Actualizar perfil
- `DELETE /api/usuarios/{rut}` - Eliminar usuario (Admin)

### **Carrito**
- `GET /api/carrito/{usuarioRut}` - Obtener carrito
- `POST /api/carrito/{usuarioRut}/agregar` - Agregar item
- `DELETE /api/carrito/{usuarioRut}/eliminar/{productoId}` - Eliminar item
- `DELETE /api/carrito/{usuarioRut}/vaciar` - Vaciar carrito

### **Órdenes**
- `GET /api/ordenes` - Listar todas (Admin)
- `GET /api/ordenes/{id}` - Obtener orden
- `GET /api/ordenes/usuario/{usuarioRut}` - Órdenes del usuario
- `POST /api/ordenes` - Crear nueva orden
- `PATCH /api/ordenes/{id}/estado/{nuevoEstado}` - Cambiar estado

---

## Validación de RUT

El sistema valida RUTs chilenos automáticamente:
- Acepta: `12345678-9` o `12.345.678-9`
- Valida el dígito verificador
- Formatea automáticamente al guardar

**Ejemplo de RUT válido:** `12345678-9`

---

## Estructura del Proyecto

```
src/main/java/Llevar_Y_Asar/Llevar_Y_Asar_back/
├── models/           # Entidades (Producto, Usuario, Orden, Carrito)
├── repositories/     # Interfaces de acceso a datos MongoDB
├── services/         # Lógica de negocio
├── controllers/      # Endpoints REST
├── config/           # Configuración Swagger
└── utils/            # Validador de RUT
```

---

## Variables de Entorno (Opcional)

Puedes configurar en `application.properties`:
```properties
# Puerto de la aplicación
server.port=8080

# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/llevarayasar
spring.data.mongodb.database=llevarayasar
```

---

## Próximos Pasos

1. ✅ Backend completado con Swagger y MongoDB
2. 🔄 Conectar frontend React con los endpoints
3. 🔐 Implementar JWT para autenticación más segura
4. 📧 Agregar notificaciones por email
5. 💳 Integrar pasarela de pagos

---

## Soporte

- **Documentación Swagger:** http://localhost:8080/swagger-ui.html
- **Logs:** Verifica la consola al ejecutar `.\mvnw spring-boot:run`
- **Base de datos:** Usa MongoDB Compass para visualizar los datos
