package Llevar_Y_Asar.Llevar_Y_Asar_back.services;

import Llevar_Y_Asar.Llevar_Y_Asar_back.models.Usuario;
import Llevar_Y_Asar.Llevar_Y_Asar_back.repositories.UsuarioRepository;
import Llevar_Y_Asar.Llevar_Y_Asar_back.utils.RutValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }
    
    public Optional<Usuario> obtenerPorRut(String rut) {
        return usuarioRepository.findByRut(rut);
    }
    
    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    public Usuario registrar(Usuario usuario) {
        // Validar RUT
        if (!RutValidator.esValido(usuario.getRut())) {
            throw new RuntimeException("RUT inválido");
        }
        
        // Formatear RUT
        usuario.setRut(RutValidator.formatear(usuario.getRut()));
        
        // Verificar si el RUT ya existe
        if (usuarioRepository.existsByRut(usuario.getRut())) {
            throw new RuntimeException("El RUT ya está registrado");
        }
        
        return usuarioRepository.save(usuario);
    }
    
    public Usuario actualizar(String rut, Usuario usuario) {
        if (usuarioRepository.existsByRut(rut)) {
            usuario.setRut(rut);
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("Usuario no encontrado");
    }
    
    public void eliminar(String rut) {
        usuarioRepository.deleteById(rut);
    }
    
    public void desactivar(String rut) {
        Optional<Usuario> usuario = usuarioRepository.findByRut(rut);
        if (usuario.isPresent()) {
            usuario.get().setActivo(false);
            usuarioRepository.save(usuario.get());
        }
    }
    
    public boolean validarLogin(String rut, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByRut(rut);
        if (usuario.isPresent()) {
            // En producción, usar BCrypt o similar en lugar de comparación directa
            return usuario.get().getPassword().equals(password);
        }
        return false;
    }
}
