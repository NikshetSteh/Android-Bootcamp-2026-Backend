package com.nikshet.industrialbackend.config

import com.nikshet.industrialbackend.security.JwtAuthFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthFilter: JwtAuthFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // Stateless API → no CSRF protection needed
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No sessions
            }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/auth/login", "/users/registration").permitAll()
                    .anyRequest().authenticated() // All other endpoints require valid JWT
            }
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .httpBasic { it.disable() }   // Not used
            .formLogin { it.disable() }   // Not used
            .logout { it.disable() }      // Not used

        return http.build()
    }
}