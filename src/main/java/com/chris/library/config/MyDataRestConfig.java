package com.chris.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.chris.library.entities.Book;
import com.chris.library.entities.Messages;
import com.chris.library.entities.Recomendation;
import com.chris.library.entities.Review;
import com.chris.library.jwt.AuthenticationFilter;


@Configuration
@EnableWebSecurity
public class MyDataRestConfig implements RepositoryRestConfigurer{

    @Autowired
    private AuthenticationFilter authenticationFilter;

    private String theAllowedOrigins= "http://localhost:3000";

    
    public MyDataRestConfig() {
        System.out.println("=== SecurityConfiguration LOADED ===");
    }
    
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
                    CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {
            HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH, HttpMethod.PUT	
        };
        
        // Set base path
        config.setBasePath("/api");

        config.exposeIdsFor(Book.class);
        config.exposeIdsFor(Review.class);
        config.exposeIdsFor(Messages.class);
        config.exposeIdsFor(Recomendation.class);
        
        
        disableHttpMethods(Book.class, config, theUnsupportedActions);
        disableHttpMethods(Review.class, config, theUnsupportedActions);
        disableHttpMethods(Messages.class, config, theUnsupportedActions);
        disableHttpMethods(Recomendation.class,config, theUnsupportedActions);
        
        /**
         * configure cors - allow all methods for development
         * */
        cors.addMapping(config.getBasePath() + "/**")
            .allowedOrigins(theAllowedOrigins)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);		
    }
	
	
    private void disableHttpMethods(Class MyClass,
            RepositoryRestConfiguration config,
            HttpMethod[] theUnsupportedActions) {
        
        config.getExposureConfiguration()
        .forDomainType(MyClass)
            .withItemExposure((metdata, httpMethods) ->
        httpMethods.disable(theUnsupportedActions))
            .withCollectionExposure((metdata, httpMethods) ->
        httpMethods.disable(theUnsupportedActions));
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("http://localhost:3000");
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource urlSource = new UrlBasedCorsConfigurationSource();
        urlSource.registerCorsConfiguration("/**", corsConfig);
        return urlSource;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationEntryPoint authEntryPoint() {
        return (request, response, authEx) -> {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            String jsonError = String.format(
                "{\"error\":\"Unauthorized\",\"message\":\"%s\",\"status\":401}",
                authEx.getMessage()
            );
            response.getWriter().write(jsonError);
        };
    }

    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        
        // THIS IS KEY - require authentication for secure endpoints
        // FIX: Use specific paths instead of **
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/books/secure/**").authenticated()  // Specific path
            .requestMatchers("/api/books/auth/**").permitAll()
            .anyRequest().permitAll()
        );
        
        // Add JWT filter BEFORE authentication check
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        // STATELESS is crucial for JWT
        http.sessionManagement(session -> 
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        
        // This will return 401 when authentication is required but missing
        http.exceptionHandling(exception -> 
            exception.authenticationEntryPoint(authEntryPoint())
        );
        
        return http.build();
    }
    
    

}
	
	
