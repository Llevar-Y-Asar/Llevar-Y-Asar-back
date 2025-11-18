package Llevar_Y_Asar.Llevar_Y_Asar_back.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Item del carrito")
public class CarritoItem {
    
    @Schema(description = "ID del producto", example = "507f1f77bcf86cd799439011")
    private String productoId;
    
    @Schema(description = "Nombre del producto", example = "Asado Premium")
    private String nombre;
    
    @Schema(description = "Precio unitario", example = "25.50")
    private Double precio;
    
    @Schema(description = "Cantidad", example = "2")
    private Integer cantidad;
    
    @Schema(description = "URL de la imagen", example = "/assets/image/asado.jpg")
    private String imagen;
}
