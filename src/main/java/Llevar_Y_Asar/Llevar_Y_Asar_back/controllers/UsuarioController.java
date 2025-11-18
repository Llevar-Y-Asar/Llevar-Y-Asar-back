package Llevar_Y_Asar.Llevar_Y_Asar_back.controllers;

import Llevar_Y_Asar.Llevar_Y_Asar_back.models.Usuario;
import Llevar_Y_Asar.Llevar_Y_Asar_back.services.UsuarioService;
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
    
    @PostMapping("/registro")
    @Operation(summary = "Registrar nuevo usuario", description = "Crea una nueva cuenta de usuario con RUT chileno")
    @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.registrar(usuario);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Usuario registrado exitosamente");
            response.put("usuario", nuevoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario con RUT y contraseña")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String rut = credenciales.get("rut");
        String password = credenciales.get("password");
        
        if (rut == null || password == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "RUT y contraseña son requeridos");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        
        if (usuarioService.validarLogin(rut, password)) {
            Optional<Usuario> usuario = usuarioService.obtenerPorRut(rut);
            if (usuario.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("mensaje", "Inicio de sesión exitoso");
                response.put("usuario", usuario.get());
                return ResponseEntity.ok(response);
            }
        }
        
        Map<String, String> error = new HashMap<>();
        error.put("error", "RUT o contraseña incorrectos");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
    
    @GetMapping("/{rut}")
    @Operation(summary = "Obtener perfil de usuario", description = "Retorna los datos del usuario por su RUT")
    public ResponseEntity<?> obtenerPorRut(@PathVariable String rut) {
        Optional<Usuario> usuario = usuarioService.obtenerPorRut(rut);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Usuario no encontrado");
    }
    
    @GetMapping
    @Operation(summary = "Listar todos los usuarios (Admin)", description = "Retorna la lista de todos los usuarios registrados")
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        return ResponseEntity.ok(usuarios);
    }
    
    @PutMapping("/{rut}")
    @Operation(summary = "Actualizar perfil de usuario", description = "Modifica los datos de un usuario existente")
    public ResponseEntity<?> actualizar(@PathVariable String rut, @RequestBody Usuario usuario) {
        try {
            Usuario actualizado = usuarioService.actualizar(rut, usuario);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @DeleteMapping("/{rut}")
    @Operation(summary = "Eliminar usuario (Admin)", description = "Elimina una cuenta de usuario del sistema")
    public ResponseEntity<?> eliminar(@PathVariable String rut) {
        try {
            usuarioService.eliminar(rut);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Usuario eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PatchMapping("/{rut}/desactivar")
    @Operation(summary = "Desactivar cuenta (Admin)", description = "Desactiva una cuenta de usuario")
    public ResponseEntity<?> desactivar(@PathVariable String rut) {
        try {
            usuarioService.desactivar(rut);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Usuario desactivado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
