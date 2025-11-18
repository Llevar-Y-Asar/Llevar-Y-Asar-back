package Llevar_Y_Asar.Llevar_Y_Asar_back.controllers;

import Llevar_Y_Asar.Llevar_Y_Asar_back.models.Orden;
import Llevar_Y_Asar.Llevar_Y_Asar_back.services.OrdenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordenes")
@Tag(name = "Órdenes", description = "Gestión de órdenes de compra")
@CrossOrigin(origins = "http://localhost:5173")
public class OrdenController {
    
    @Autowired
    private OrdenService ordenService;
    
    @GetMapping
    @Operation(summary = "Obtener todas las órdenes", description = "Retorna la lista de todas las órdenes (solo admin)")
    public ResponseEntity<List<Orden>> obtenerTodas() {
        return ResponseEntity.ok(ordenService.obtenerTodas());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener orden por ID", description = "Retorna una orden específica")
    @ApiResponse(responseCode = "200", description = "Orden encontrada")
    @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    public ResponseEntity<Orden> obtenerPorId(@PathVariable String id) {
        Optional<Orden> orden = ordenService.obtenerPorId(id);
        return orden.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener órdenes del usuario", description = "Retorna todas las órdenes de un usuario específico")
    public ResponseEntity<List<Orden>> obtenerPorUsuario(@PathVariable String usuarioId) {
        return ResponseEntity.ok(ordenService.obtenerPorUsuario(usuarioId));
    }
    
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener órdenes por estado", description = "Filtra órdenes por estado (PENDIENTE, CONFIRMADA, ENVIADA, ENTREGADA, CANCELADA)")
    public ResponseEntity<List<Orden>> obtenerPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(ordenService.obtenerPorEstado(estado));
    }
    
    @PostMapping
    @Operation(summary = "Crear nueva orden", description = "Crea una nueva orden de compra")
    @ApiResponse(responseCode = "200", description = "Orden creada exitosamente")
    public ResponseEntity<Orden> crear(@RequestBody Orden orden) {
        return ResponseEntity.ok(ordenService.crear(orden));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar orden", description = "Actualiza los datos de una orden")
    @ApiResponse(responseCode = "200", description = "Orden actualizada")
    @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    public ResponseEntity<Orden> actualizar(@PathVariable String id, @RequestBody Orden orden) {
        Orden actualizada = ordenService.actualizar(id, orden);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}/estado/{nuevoEstado}")
    @Operation(summary = "Cambiar estado de orden", description = "Actualiza el estado de una orden")
    @ApiResponse(responseCode = "200", description = "Estado actualizado")
    @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    public ResponseEntity<Orden> cambiarEstado(@PathVariable String id, @PathVariable String nuevoEstado) {
        Orden orden = ordenService.cambiarEstado(id, nuevoEstado);
        if (orden != null) {
            return ResponseEntity.ok(orden);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar orden", description = "Cancela una orden existente")
    @ApiResponse(responseCode = "204", description = "Orden cancelada")
    public ResponseEntity<Void> cancelar(@PathVariable String id) {
        ordenService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
