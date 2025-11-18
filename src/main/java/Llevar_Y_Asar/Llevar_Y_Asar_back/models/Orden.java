package Llevar_Y_Asar.Llevar_Y_Asar_back.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ordenes")
@Schema(description = "Orden de compra")
public class Orden {
    
    @Id
    @Schema(description = "ID de la orden", example = "507f1f77bcf86cd799439013")
    private String id;
    
    @Schema(description = "ID del usuario", example = "507f1f77bcf86cd799439012")
    private String usuarioId;
    
    @Schema(description = "Número de orden único", example = "ORD-001234")
    private String numeroOrden;
    
    @Schema(description = "Items de la orden")
    private List<CarritoItem> items;
    
    @Schema(description = "Total de la orden", example = "51.00")
    private Double total;
    
    @Schema(description = "Estado de la orden", example = "PENDIENTE", allowableValues = {"PENDIENTE", "CONFIRMADA", "ENVIADA", "ENTREGADA", "CANCELADA"})
    private String estado = "PENDIENTE";
    
    @Schema(description = "Dirección de entrega", example = "Calle Principal 123, Madrid")
    private String direccionEntrega;
    
    @Schema(description = "Fecha de creación")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Schema(description = "Fecha estimada de entrega")
    private LocalDateTime fechaEntregaEstimada;
    
    @Schema(description = "Notas o comentarios especiales", example = "Entregar después de las 5 PM")
    private String notas;
}
