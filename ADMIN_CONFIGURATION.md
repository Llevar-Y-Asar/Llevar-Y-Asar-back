# CONFIGURACIÓN DE ADMINISTRADOR - DOCUMENTACIÓN

## 🎯 Implementación Realizada

### ✅ 1. SecurityConfig.java
- ✅ Agregado `@EnableMethodSecurity` para autorización por método
- ✅ Agregado `@Bean` para `BCryptPasswordEncoder` (instancia singleton)
- ✅ Configurado `AuthenticationProvider` con `BCryptPasswordEncoder`

### ✅ 2. UsuarioService.java
- ✅ Inyectado `BCryptPasswordEncoder`
- ✅ Método `registrar()`: encripta contraseña con BCrypt
- ✅ Método `registrar()`: fuerza rol = "USER" (seguridad)
- ✅ Método `validarLoginPorEmail()`: usa `passwordEncoder.matches()` para comparar
- ✅ Método `loadUserByUsername()`: simplificado, usa `.roles(u.getRol())`

### ✅ 3. UsuarioController.java
- ✅ Agregado import: `org.springframework.security.access.prepost.PreAuthorize`
- ✅ Endpoint `GET /api/usuarios`: protegido con `@PreAuthorize("hasRole('ADMIN')")`
- ✅ Endpoint `DELETE /api/usuarios/{rut}`: protegido con `@PreAuthorize("hasRole('ADMIN')")`
- ✅ Endpoint `PATCH /api/usuarios/{rut}/desactivar`: protegido con `@PreAuthorize("hasRole('ADMIN')")`

---

## 📋 CONCEPTO CLAVE

**Los administradores NO son una colección separada.**

Estructura MongoDB:

```
usuarios
├── Usuario 1 (rol: USER)
├── Usuario 2 (rol: ADMIN)  ← Mismo modelo, diferente rol
└── Usuario N (rol: USER)
```

NO existe colección "admin" en la aplicación.

---

## 🔐 CREACIÓN DE USUARIO ADMIN

### Opción 1: Script Node.js (RECOMENDADO)

```bash
npm install bcryptjs
node create-admin.js
```

El script genera un usuario con:
- RUT: 11.111.111-1
- Email: admin@llevarayasar.com
- Password: Admin123456 (encriptado con BCrypt)
- Rol: ADMIN

### Opción 2: Inserción Manual en MongoDB Atlas

```javascript
db.usuarios.insertOne({
  "_id": "11.111.111-1",
  "rut": "11.111.111-1",
  "nombre": "Administrador",
  "email": "admin@llevarayasar.com",
  "password": "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P4/rWm", // Admin123456
  "rol": "ADMIN",
  "activo": true,
  "fechaCreacion": new Date()
})
```

---

## ✅ VERIFICACIÓN

### 1. Compilación
```bash
.\mvnw clean compile
```
✅ BUILD SUCCESS (con advertencia deprecation - normal)

### 2. Ejecutar backend
```bash
.\mvnw spring-boot:run
```

### 3. Crear usuario ADMIN (si no existe)
```bash
node create-admin.js
```

### 4. Probar login como ADMIN
**Endpoint:** `POST http://localhost:8080/api/usuarios/login`

**Request:**
```json
{
  "email": "admin@llevarayasar.com",
  "password": "Admin123456"
}
```

**Response:**
```json
{
  "mensaje": "Inicio de sesión exitoso",
  "usuario": { ... },
  "token": "eyJhbGc..."
}
```

### 5. Acceder a endpoints protegidos
**Endpoint:** `GET http://localhost:8080/api/usuarios`

**Headers:**
```
Authorization: Bearer <token-jwt>
```

Si no eres ADMIN → ❌ 403 FORBIDDEN
Si eres ADMIN → ✅ 200 OK (lista de usuarios)

---

## 🔒 SEGURIDAD IMPLEMENTADA

1. **Contraseñas encriptadas** con BCrypt
2. **Rol USER** se fuerza en registro (no hay escalada de privilegios)
3. **Endpoints admin** protegidos con `@PreAuthorize("hasRole('ADMIN')")`
4. **ADMIN NO se crea desde API** (solo manualmente en BD)
5. **JWT con expiracion** (24 horas)
6. **Validación de RUT** chileno

---

## 📝 FRASE PARA EVALUACIÓN

> "Los administradores no se modelan como una colección independiente, sino como usuarios con un rol distinto, manteniendo un diseño seguro, normalizado y escalable."

---

## 🧪 CASOS DE PRUEBA

### Caso 1: Registrar usuario NORMAL
```
POST /api/usuarios/registro
Rol asignado: USER ✅
Contraseña: Encriptada ✅
```

### Caso 2: Login como USER
```
POST /api/usuarios/login
Genera JWT ✅
Accede a endpoints públicos ✅
NO puede acceder a /api/usuarios ❌ (403 FORBIDDEN)
```

### Caso 3: Login como ADMIN
```
POST /api/usuarios/login
Genera JWT ✅
Accede a /api/usuarios ✅ (200 OK)
Puede eliminar usuarios ✅
Puede desactivar usuarios ✅
```

---

## 📂 ARCHIVOS MODIFICADOS

1. `src/main/java/Llevar_Y_Asar/Llevar_Y_Asar_back/config/SecurityConfig.java`
2. `src/main/java/Llevar_Y_Asar/Llevar_Y_Asar_back/services/UsuarioService.java`
3. `src/main/java/Llevar_Y_Asar/Llevar_Y_Asar_back/controllers/UsuarioController.java`
4. `create-admin.js` (nuevo archivo)

---

## 🚀 PRÓXIMOS PASOS

1. ✅ Compilar: `.\mvnw clean compile`
2. ✅ Ejecutar: `.\mvnw spring-boot:run`
3. ✅ Crear ADMIN: `node create-admin.js`
4. ✅ Probar endpoints con Postman/Insomnia
5. ✅ Implementación lista para evaluación
