package Llevar_Y_Asar.Llevar_Y_Asar_back.controllers;

import Llevar_Y_Asar.Llevar_Y_Asar_back.models.Carrito;
import Llevar_Y_Asar.Llevar_Y_Asar_back.models.CarritoItem;
import Llevar_Y_Asar.Llevar_Y_Asar_back.services.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@Tag(name = "Carrito", description = "Gestión del carrito de compras")
@CrossOrigin(origins = "http://localhost:5173")
public class CarritoController {
    
    @Autowired
    private CarritoService carritoService;
    
    @GetMapping("/{usuarioId}")
    @Operation(summary = "Obtener carrito", description = "Obtiene el carrito de un usuario")
    @ApiResponse(responseCode = "200", description = "Carrito obtenido")
    public ResponseEntity<Carrito> obtenerCarrito(@PathVariable String usuarioId) {
        Carrito carrito = carritoService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(carrito);
    }
    
    @PostMapping("/{usuarioId}/agregar")
    @Operation(summary = "Agregar item al carrito", description = "Añade un producto al carrito del usuario")
    @ApiResponse(responseCode = "200", description = "Item agregado")
    public ResponseEntity<Carrito> agregarItem(@PathVariable String usuarioId, @RequestBody CarritoItem item) {
        Carrito carrito = carritoService.agregarItem(usuarioId, item);
        return ResponseEntity.ok(carrito);
    }
    
    @DeleteMapping("/{usuarioId}/eliminar/{productoId}")
    @Operation(summary = "Eliminar item del carrito", description = "Remueve un producto del carrito")
    @ApiResponse(responseCode = "200", description = "Item eliminado")
    public ResponseEntity<Carrito> eliminarItem(@PathVariable String usuarioId, @PathVariable String productoId) {
        Carrito carrito = carritoService.eliminarItem(usuarioId, productoId);
        return ResponseEntity.ok(carrito);
    }
    
    @DeleteMapping("/{usuarioId}/vaciar")
    @Operation(summary = "Vaciar carrito", description = "Elimina todos los items del carrito")
    @ApiResponse(responseCode = "200", description = "Carrito vaciado")
    public ResponseEntity<Carrito> vaciar(@PathVariable String usuarioId) {
        Carrito carrito = carritoService.vaciar(usuarioId);
        return ResponseEntity.ok(carrito);
    }
}
