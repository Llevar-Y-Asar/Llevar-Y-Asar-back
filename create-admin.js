const { MongoClient } = require('mongodb');
const bcrypt = require('bcryptjs');

const MONGODB_URI = 'mongodb+srv://pabgarciam_db_user:Pq9pEQl8W3mWhpTt@cluster0.0pkavoy.mongodb.net/llevarayasar';

async function createAdmin() {
  const client = new MongoClient(MONGODB_URI);

  try {
    await client.connect();
    console.log('Connected to MongoDB');

    const db = client.db('llevarayasar');
    const usuariosCollection = db.collection('usuarios');

    // Hash the password using bcryptjs
    const plainPassword = 'Admin123456';
    const hashedPassword = await bcrypt.hash(plainPassword, 10);

    // Admin user object
    const adminUser = {
      rut: '11.111.111-1',
      nombre: 'Administrador',
      email: 'admin@llevarayasar.com',
      password: hashedPassword,
      rol: 'ADMIN',
      activo: true,
      createdAt: new Date(),
      updatedAt: new Date(),
    };

    // Insert admin user
    const result = await usuariosCollection.insertOne(adminUser);
    console.log('Admin user created successfully:', result.insertedId);
    console.log('Email: admin@llevarayasar.com');
    console.log('Password: Admin123456');

  } catch (error) {
    console.error('Error creating admin user:', error);
  } finally {
    await client.close();
  }
}

createAdmin();
