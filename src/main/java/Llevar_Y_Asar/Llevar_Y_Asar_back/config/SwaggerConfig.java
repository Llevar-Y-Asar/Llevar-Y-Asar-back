package Llevar_Y_Asar.Llevar_Y_Asar_back.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Llevar y Asar - API")
                        .description("API RESTful para la plataforma de venta de asados en Chile")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Soporte Llevar y Asar")
                                .email("soporte@llevarayasar.cl")));
    }
}
