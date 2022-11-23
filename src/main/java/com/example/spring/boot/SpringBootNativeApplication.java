package com.example.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.NativeDetector;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.time.Instant;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
@EnableWebFlux
public class SpringBootNativeApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(SpringBootNativeApplication.class, args);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> isReady()
    {
        return event -> {
            System.out.println("Application is ready and running " + (NativeDetector.inNativeImage() ? "natively" : "non-natively"));
        };
    }

    @Bean
    public RouterFunction<ServerResponse> homepage()
    {
        return route().GET("/", accept(MediaType.TEXT_PLAIN), request -> {
            LombokExample message = LombokExample.builder().withMessage("Hello Spring Native @ " + Instant.now()).build();
            return ok().contentType(MediaType.APPLICATION_JSON).bodyValue(message.getMessage());
        }).build();
    }

}
