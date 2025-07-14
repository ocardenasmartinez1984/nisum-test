package cl.cardenas.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "User API",
                version = "1.0",
                description = "Secure API"
        )
)
@Configuration
public class SwaggerConfig {
}
