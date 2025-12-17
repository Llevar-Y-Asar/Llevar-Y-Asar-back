package Llevar_Y_Asar.Llevar_Y_Asar_back.controllers;

import Llevar_Y_Asar.Llevar_Y_Asar_back.models.Usuario;
import Llevar_Y_Asar.Llevar_Y_Asar_back.services.UsuarioService;
import Llevar_Y_Asar.Llevar_Y_Asar_back.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Gestión de cuentas de usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/registro")
    @Operation(summary = "Registrar nuevo usuario", description = "Crea una nueva cuenta con RUT y email")
    @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        try {
            Usuario nuevo = usuarioService.registrar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("usuario", nuevo));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica con email y contraseña")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");
        
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email y contraseña son requeridos"));
        }
        
        if (usuarioService.validarLoginPorEmail(email, password)) {
            Optional<Usuario> usuario = usuarioService.obtenerPorEmail(email);
            if (usuario.isPresent()) {
                String token = jwtUtil.generateToken(email);
                Map<String, Object> response = new HashMap<>();
                response.put("mensaje", "Inicio de sesión exitoso");
                response.put("usuario", usuario.get());
                response.put("token", token);
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "Email o contraseña incorrectos"));
    }
    
    @GetMapping("/perfil/{email}")
    @Operation(summary = "Obtener perfil por email")
    public ResponseEntity<?> obtenerPorEmail(@PathVariable String email) {
        Optional<Usuario> u = usuarioService.obtenerPorEmail(email);
        return u.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/perfil/{email}")
    @Operation(summary = "Actualizar perfil por email")
    public ResponseEntity<?> actualizar(@PathVariable String email, @RequestBody Usuario datos) {
        try {
            Usuario actualizado = usuarioService.actualizarPorEmail(email, datos);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // Admin endpoints
    @GetMapping
    @Operation(summary = "Listar todos los usuarios (Admin)")
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }
    
    @DeleteMapping("/{rut}")
    public ResponseEntity<?> eliminar(@PathVariable String rut) {
        usuarioService.eliminar(rut);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario eliminado"));
    }
    
    @PatchMapping("/{rut}/desactivar")
    public ResponseEntity<?> desactivar(@PathVariable String rut) {
        usuarioService.desactivar(rut);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario desactivado"));
    }
}