package bartzmoveis.apigetitem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bartzmoveis.apigetitem.security.ApiKeyFilter;

// 
@Configuration
public class SecurityConfig {

    @Autowired
    private ApiKeyProperties apiKeyProperties;

    @Bean
    public ApiKeyFilter apiKeyFilter() {
        return new ApiKeyFilter(apiKeyProperties.getKey());
    }
}
