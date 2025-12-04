# Ejemplos de Request para Probar API

## 1. REGISTRO DE USUARIO

### Request POST: `/api/usuarios/registro`
```json
{
  "rut": "12345678-9",
  "nombre": "Juan Pérez García",
  "email": "juan@example.com",
  "telefono": "+56 9 1234 5678",
  "password": "MiPassword123!",
  "direccion": "Avenida Principal 123, Depto 4B",
  "ciudad": "Santiago",
  "region": "Región Metropolitana",
  "codigoPostal": "8320000"
}
```

### Response Esperada (201 Created):
```json
{
  "mensaje": "Usuario registrado exitosamente",
  "usuario": {
    "rut": "12.345.678-9",
    "nombre": "Juan Pérez García",
    "email": "juan@example.com",
    "telefono": "+56 9 1234 5678",
    "direccion": "Avenida Principal 123, Depto 4B",
    "ciudad": "Santiago",
    "region": "Región Metropolitana",
    "codigoPostal": "8320000",
    "rol": "USER",
    "activo": true
  }
}
```

---

## 2. LOGIN

### Request POST: `/api/usuarios/login`
```json
{
  "rut": "12345678-9",
  "password": "MiPassword123!"
}
```

### Response Esperada (200 OK):
```json
{
  "mensaje": "Inicio de sesión exitoso",
  "usuario": {
    "rut": "12.345.678-9",
    "nombre": "Juan Pérez García",
    "email": "juan@example.com",
    "activo": true
  }
}
```

---

## 3. CREAR PRODUCTO (ADMIN)

### Request POST: `/api/productos/admin`
```json
{
  "nombre": "Asado Premium de Res",
  "descripcion": "Corte premium de res de primera calidad",
  "precio": 45.99,
  "imagen": "/assets/image/asado-premium.jpg",
  "categoria": "Carnes Premium",
  "stock": 50,
  "rating": 4.8,
  "resenias": 127,
  "activo": true
}
```

### Response Esperada (201 Created):
```json
{
  "mensaje": "Producto creado exitosamente",
  "producto": {
    "id": "507f1f77bcf86cd799439011",
    "nombre": "Asado Premium de Res",
    "descripcion": "Corte premium de res de primera calidad",
    "precio": 45.99,
    "imagen": "/assets/image/asado-premium.jpg",
    "categoria": "Carnes Premium",
    "stock": 50,
    "rating": 4.8,
    "resenias": 127,
    "activo": true
  }
}
```

---

## 4. OBTENER PRODUCTOS

### Request GET: `/api/productos`
Sin body necesario

### Response Esperada (200 OK):
```json
[
  {
    "id": "507f1f77bcf86cd799439011",
    "nombre": "Asado Premium de Res",
    "descripcion": "Corte premium de res de primera calidad",
    "precio": 45.99,
    "imagen": "/assets/image/asado-premium.jpg",
    "categoria": "Carnes Premium",
    "stock": 50,
    "rating": 4.8,
    "resenias": 127,
    "activo": true
  }
]
```

---

## 5. AGREGAR ITEM AL CARRITO

### Request POST: `/api/carrito/{usuarioRut}/agregar`
Reemplazar `{usuarioRut}` con `12345678-9`

```json
{
  "productoId": "507f1f77bcf86cd799439011",
  "nombre": "Asado Premium de Res",
  "precio": 45.99,
  "cantidad": 2,
  "imagen": "/assets/image/asado-premium.jpg"
}
```

### Response Esperada (200 OK):
```json
{
  "mensaje": "Item agregado al carrito",
  "carrito": {
    "id": "507f1f77bcf86cd799439012",
    "usuarioId": "12.345.678-9",
    "items": [
      {
        "productoId": "507f1f77bcf86cd799439011",
        "nombre": "Asado Premium de Res",
        "precio": 45.99,
        "cantidad": 2,
        "imagen": "/assets/image/asado-premium.jpg"
      }
    ],
    "total": 91.98,
    "fechaCreacion": "2025-11-18T14:30:00",
    "fechaActualizacion": "2025-11-18T14:32:00"
  }
}
```

---

## 6. OBTENER CARRITO

### Request GET: `/api/carrito/{usuarioRut}`
Reemplazar `{usuarioRut}` con `12345678-9`

### Response Esperada (200 OK):
```json
{
  "id": "507f1f77bcf86cd799439012",
  "usuarioId": "12.345.678-9",
  "items": [
    {
      "productoId": "507f1f77bcf86cd799439011",
      "nombre": "Asado Premium de Res",
      "precio": 45.99,
      "cantidad": 2,
      "imagen": "/assets/image/asado-premium.jpg"
    }
  ],
  "total": 91.98,
  "fechaCreacion": "2025-11-18T14:30:00",
  "fechaActualizacion": "2025-11-18T14:32:00"
}
```

---

## 7. CREAR ORDEN

### Request POST: `/api/ordenes`
```json
{
  "usuarioId": "12.345.678-9",
  "items": [
    {
      "productoId": "507f1f77bcf86cd799439011",
      "nombre": "Asado Premium de Res",
      "precio": 45.99,
      "cantidad": 2,
      "imagen": "/assets/image/asado-premium.jpg"
    }
  ],
  "total": 91.98,
  "direccionEntrega": "Avenida Principal 123, Depto 4B",
  "notas": "Entregar después de las 5 PM"
}
```

### Response Esperada (201 Created):
```json
{
  "mensaje": "Orden creada exitosamente",
  "orden": {
    "id": "507f1f77bcf86cd799439013",
    "usuarioId": "12.345.678-9",
    "numeroOrden": "ORD-A1B2C3D4",
    "items": [...],
    "total": 91.98,
    "estado": "PENDIENTE",
    "direccionEntrega": "Avenida Principal 123, Depto 4B",
    "fechaCreacion": "2025-11-18T14:35:00",
    "notas": "Entregar después de las 5 PM"
  }
}
```

---

## 8. CAMBIAR ESTADO DE ORDEN (ADMIN)

### Request PATCH: `/api/ordenes/{id}/estado/{nuevoEstado}`
Ejemplo: `/api/ordenes/507f1f77bcf86cd799439013/estado/CONFIRMADA`

Estados válidos: `PENDIENTE`, `CONFIRMADA`, `ENVIADA`, `ENTREGADA`, `CANCELADA`

### Response Esperada (200 OK):
```json
{
  "mensaje": "Estado de orden actualizado",
  "orden": {
    "id": "507f1f77bcf86cd799439013",
    "estado": "CONFIRMADA",
    "...": "otros campos"
  }
}
```

---

## Notas Importantes

1. **RUT chileno:** Debe estar en formato `12345678-9` o `12.345.678-9`
2. **CORS:** El backend permite requests desde cualquier origen (`origins = "*"`)
3. **MongoDB:** Asegúrate que el servicio esté ejecutándose
4. **Puertos:** Por defecto, la app corre en `http://localhost:8080`

---

## Usar Postman/Insomnia

1. Copia cualquiera de los ejemplos arriba
2. Abre Postman o Insomnia
3. Selecciona el método (GET, POST, PUT, DELETE, PATCH)
4. Ingresa la URL: `http://localhost:8080{endpoint}`
5. En la pestaña "Body" selecciona "JSON" y pega el ejemplo
6. Envía y verifica la respuesta
