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
        if (!RutValidator.esValido(usuario.getRut())) {
            throw new RuntimeException("RUT inválido");
        }
        usuario.setRut(RutValidator.formatear(usuario.getRut()));
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarPorEmail(String email, Usuario datos) {
        return usuarioRepository.findByEmail(email)
            .map(u -> {
                u.setNombre(datos.getNombre());
                u.setTelefono(datos.getTelefono());
                u.setDireccion(datos.getDireccion());
                u.setCiudad(datos.getCiudad());
                u.setRegion(datos.getRegion());
                return usuarioRepository.save(u);
            })
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }

    public void eliminar(String rut) {
        usuarioRepository.deleteById(rut);
    }

    public void desactivar(String rut) {
        usuarioRepository.findByRut(rut)
            .ifPresent(u -> {
                u.setActivo(false);
                usuarioRepository.save(u);
            });
    }

    // VALIDAR LOGIN POR EMAIL
    public boolean validarLoginPorEmail(String email, String password) {
        return usuarioRepository.findByEmail(email)
            .map(u -> u.getPassword().equals(password))
            .orElse(false);
    }

    // SPRING SECURITY: LOAD BY EMAIL
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        var authorities = Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_" + u.getRol())
        );

        return org.springframework.security.core.userdetails.User
            .withUsername(u.getEmail())
            .password(u.getPassword())
            .authorities(authorities)
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(!u.getActivo())
            .build();
    }
}