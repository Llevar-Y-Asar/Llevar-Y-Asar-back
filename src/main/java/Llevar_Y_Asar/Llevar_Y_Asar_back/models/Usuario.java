package Llevar_Y_Asar.Llevar_Y_Asar_back.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
@Schema(description = "Usuario del sistema")
public class Usuario {
    
    @Id
    @Schema(description = "RUT del usuario (ID único)", example = "12.345.678-9")
    private String rut;
    
    @Schema(description = "Nombre completo", example = "Juan Pérez García")
    private String nombre;
    
    @Schema(description = "Email del usuario", example = "juan@example.com")
    private String email;
    
    @Schema(description = "Teléfono de contacto", example = "+56 9 1234 5678")
    private String telefono;
    
    @Schema(description = "Contraseña encriptada")
    private String password;
    
    @Schema(description = "Dirección de entrega", example = "Avenida Principal 123, Depto 4B")
    private String direccion;
    
    @Schema(description = "Ciudad", example = "Santiago")
    private String ciudad;
    
    @Schema(description = "Región", example = "Región Metropolitana")
    private String region;
    
    @Schema(description = "Código postal", example = "8320000")
    private String codigoPostal;
    
    @Schema(description = "Rol del usuario", example = "USER", allowableValues = {"USER", "ADMIN"})
    private String rol = "USER";
    
    @Schema(description = "Fecha de creación")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Schema(description = "Indica si la cuenta está activa", example = "true")
    private Boolean activo = true;
}
