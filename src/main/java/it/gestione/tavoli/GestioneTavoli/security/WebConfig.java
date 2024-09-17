package it.gestione.tavoli.GestioneTavoli.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Permetti richieste CORS per tutte le rotte
                .allowedOrigins("*")  // Sostituisci con l'URL del tuo front-end
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Metodi HTTP permessi
                .allowedHeaders("*");  // Permetti tutti gli header
    }
}
