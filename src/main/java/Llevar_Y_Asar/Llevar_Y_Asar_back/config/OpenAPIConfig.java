package Llevar_Y_Asar.Llevar_Y_Asar_back.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Llevar Y Asar - API Backend")
                        .version("1.0.0")
                        .description("API REST para la plataforma de compra de productos de asado 'Llevar Y Asar'")
                        .contact(new Contact()
                                .name("Llevar Y Asar Team")
                                .url("http://localhost:8080")
                                .email("support@llevarayasar.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
