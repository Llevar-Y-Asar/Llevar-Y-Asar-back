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
@Document(collection = "carritos")
@Schema(description = "Carrito de compras")
public class Carrito {
    
    @Id
    @Schema(description = "ID del carrito", example = "507f1f77bcf86cd799439011")
    private String id;
    
    @Schema(description = "ID del usuario propietario", example = "507f1f77bcf86cd799439012")
    private String usuarioId;
    
    @Schema(description = "Lista de items del carrito")
    private List<CarritoItem> items;
    
    @Schema(description = "Total del carrito", example = "51.00")
    private Double total;
    
    @Schema(description = "Fecha de creación")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Schema(description = "Última actualización")
    private LocalDateTime fechaActualizacion = LocalDateTime.now();
}
