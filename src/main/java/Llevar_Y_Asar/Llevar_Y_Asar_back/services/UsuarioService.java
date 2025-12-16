package Llevar_Y_Asar.Llevar_Y_Asar_back.services;

import Llevar_Y_Asar.Llevar_Y_Asar_back.models.Usuario;
import Llevar_Y_Asar.Llevar_Y_Asar_back.repositories.UsuarioRepository;
import Llevar_Y_Asar.Llevar_Y_Asar_back.utils.RutValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

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
        // Validar RUT (opcional, puedes comentar si no lo usas)
        if (!RutValidator.esValido(usuario.getRut())) {
            throw new RuntimeException("RUT inválido");
        }
        usuario.setRut(RutValidator.formatear(usuario.getRut()));

        // Validar email único
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Guardar (contraseña en texto plano, solo para evaluación)
        return usuarioRepository.save(usuario);
    }

    // MÉTODO CORREGIDO: actualizar por email
    public Usuario actualizarPorEmail(String email, Usuario usuarioActualizado) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(email);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setTelefono(usuarioActualizado.getTelefono());
            usuario.setDireccion(usuarioActualizado.getDireccion());
            usuario.setCiudad(usuarioActualizado.getCiudad());
            usuario.setRegion(usuarioActualizado.getRegion());
            // No se permite cambiar email ni rol desde aquí
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("Usuario no encontrado con email: " + email);
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

    //  MÉTODO CORREGIDO: validar login por email
    public boolean validarLoginPorEmail(String email, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent()) {
            // ⚠️ En producción: usar BCryptPasswordEncoder
            return usuario.get().getPassword().equals(password);
        }
        return false;
    }

    // MÉTODO REQUERIDO POR SPRING SECURITY
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        Usuario usuario = usuarioOpt.orElseThrow(() ->
            new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        var authorities = Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_" + usuario.getRol())
        );

        return org.springframework.security.core.userdetails.User
            .withUsername(usuario.getEmail())
            .password(usuario.getPassword())
            .authorities(authorities)
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(!usuario.getActivo())
            .build();
    }
}