// seed-mongodb.js
// Script para cargar datos iniciales en MongoDB
// Uso: node seed-mongodb.js

const { MongoClient } = require('mongodb');

// Tu conexión de MongoDB Atlas
const MONGODB_URI = 'mongodb+srv://pabgarciam_db_user:Pq9pEQl8W3mWhpTt@cluster0.0pkavoy.mongodb.net/llevarayasar?appName=Cluster0&retryWrites=true&w=majority';
const DB_NAME = 'llevarayasar';

// Datos de productos del frontend (con rutas corregidas)
const productos = [
    {
        nombre: "Asado de Tira Premium",
        descripcion: "Corte jugoso y bien marmoleado, ideal para asar a la parrilla.",
        precio: 8990,
        imagen: "/assets/image/entrania2.jpg",
        stock: 15,
        categoria: "Cortes Premium",
        activo: true
    },
    {
        nombre: "Chorizos Artesanales x6",
        descripcion: "Elaborados con carne de cerdo y especias naturales. ¡Irresistibles!",
        precio: 3990,
        imagen: "/assets/image/chorizos.jpg",
        stock: 30,
        categoria: "Embutidos",
        activo: true
    },
    {
        nombre: "Parrillada Familiar",
        descripcion: "Combo para 4 personas: asado, chorizos y ensaladas.",
        precio: 24990,
        imagen: "/assets/image/parrillada.jpg",
        stock: 8,
        categoria: "Combos",
        activo: true
    },
    {
        nombre: "Entraña",
        descripcion: "Corte de entraña sabroso y tierno para la parrilla.",
        precio: 11990,
        imagen: "/assets/image/entrania1.jpg",
        stock: 12,
        categoria: "Entrañas",
        activo: true
    },
    {
        nombre: "Lomo Liso",
        descripcion: "Segunda variedad de lomo liso jugoso.",
        precio: 15500,
        imagen: "/assets/image/lomo-liso2.jpg",
        stock: 11,
        categoria: "Lomo Liso",
        activo: true
    },
    {
        nombre: "Lomo",
        descripcion: "Lomo liso especial para asados familiares.",
        precio: 16000,
        imagen: "/assets/image/punta-ganso4.jpg",
        stock: 10,
        categoria: "Lomo Liso",
        activo: true
    },
    {
        nombre: "Longaniza",
        descripcion: "Longaniza tradicional para la parrilla.",
        precio: 4990,
        imagen: "/assets/image/longaniza2.jpg",
        stock: 20,
        categoria: "Embutidos",
        activo: true
    },
    {
        nombre: "Punta de Ganso",
        descripcion: "Punta ganso tierna y jugosa para asados.",
        precio: 13990,
        imagen: "/assets/image/punta-ganso2.jpg",
        stock: 10,
        categoria: "Punta Ganso",
        activo: true
    }
];

// Usuarios de prueba
const usuarios = [
    {
        _id: "12.345.678-9",
        rut: "12.345.678-9",
        nombre: "Juan Pérez",
        email: "juan@example.com",
        telefono: "+56 9 1234 5678",
        password: "Test123456",
        direccion: "Avenida Principal 123",
        ciudad: "Santiago",
        region: "Metropolitana",
        codigoPostal: "8320000",
        rol: "USER",
        activo: true,
        fechaCreacion: new Date()
    },
    {
        _id: "98.765.432-1",
        rut: "98.765.432-1",
        nombre: "Admin User",
        email: "admin@example.com",
        telefono: "+56 9 9876 5432",
        password: "Admin123456",
        direccion: "Calle Admin 456",
        ciudad: "Santiago",
        region: "Metropolitana",
        codigoPostal: "8320000",
        rol: "ADMIN",
        activo: true,
        fechaCreacion: new Date()
    }
];

async function seedDatabase() {
    const client = new MongoClient(MONGODB_URI);

    try {
        // Conectar
        await client.connect();
        console.log('✅ Conectado a MongoDB Atlas');

        const db = client.db(DB_NAME);

        // 1. Limpiar y crear colección de productos
        console.log('\n📦 Creando colección de productos...');
        try {
            await db.collection('productos').drop();
        } catch (e) {
            // Colección no existe, no pasa nada
        }
        const productosCollection = await db.createCollection('productos');
        const productosResult = await productosCollection.insertMany(productos);
        console.log(`✅ ${productosResult.insertedCount} productos insertados`);

        // 2. Limpiar y crear colección de usuarios
        console.log('\n👤 Creando colección de usuarios...');
        try {
            await db.collection('usuarios').drop();
        } catch (e) {
            // Colección no existe
        }
        const usuariosCollection = await db.createCollection('usuarios');
        const usuariosResult = await usuariosCollection.insertMany(usuarios);
        console.log(`✅ ${usuariosResult.insertedCount} usuarios insertados`);

        // 3. Crear colecciones vacías (para que existan)
        console.log('\n🛒 Creando colecciones de carritos y órdenes...');
        try {
            await db.collection('carritos').drop();
        } catch (e) {}
        try {
            await db.collection('ordenes').drop();
        } catch (e) {}

        await db.createCollection('carritos');
        await db.createCollection('ordenes');
        console.log('✅ Colecciones de carritos y órdenes creadas');

        // 4. Resumen
        console.log('\n' + '='.repeat(50));
        console.log('🎉 BASE DE DATOS CARGADA EXITOSAMENTE');
        console.log('='.repeat(50));
        console.log('\n📊 DATOS INSERTADOS:');
        console.log(`  • Productos: ${productosResult.insertedCount}`);
        console.log(`  • Usuarios: ${usuariosResult.insertedCount}`);
        console.log('\n👤 CREDENCIALES DE PRUEBA:');
        console.log('\n  Usuario normal:');
        console.log('    RUT: 12.345.678-9');
        console.log('    Password: Test123456');
        console.log('\n  Admin:');
        console.log('    RUT: 98.765.432-1');
        console.log('    Password: Admin123456');
        console.log('\n✨ Ya puedes iniciar sesión en http://localhost:5173/login');
        console.log('='.repeat(50) + '\n');

    } catch (error) {
        console.error('❌ Error:', error.message);
        process.exit(1);
    } finally {
        await client.close();
    }
}

// Ejecutar
seedDatabase();
