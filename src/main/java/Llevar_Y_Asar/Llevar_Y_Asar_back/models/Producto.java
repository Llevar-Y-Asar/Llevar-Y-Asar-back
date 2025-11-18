package Llevar_Y_Asar.Llevar_Y_Asar_back.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "productos")
@Schema(description = "Producto del catálogo")
public class Producto {
    
    @Id
    @Schema(description = "ID único del producto", example = "507f1f77bcf86cd799439011")
    private String id;
    
    @Schema(description = "Nombre del producto", example = "Asado Premium")
    private String nombre;
    
    @Schema(description = "Descripción detallada", example = "Corte premium de res")
    private String descripcion;
    
    @Schema(description = "Precio en USD", example = "25.50")
    private Double precio;
    
    @Schema(description = "URL de la imagen", example = "/assets/image/asado-premium.jpg")
    private String imagen;
    
    @Schema(description = "Categoría del producto", example = "Carnes")
    private String categoria;
    
    @Schema(description = "Stock disponible", example = "100")
    private Integer stock;
    
    @Schema(description = "Calificación del producto", example = "4.5")
    private Double rating;
    
    @Schema(description = "Cantidad de reseñas", example = "42")
    private Integer resenias;
    
    @Schema(description = "Indica si el producto está activo", example = "true")
    private Boolean activo = true;
}
