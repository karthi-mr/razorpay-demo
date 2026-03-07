package com.learn.demo.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class EnvConfig {

    static {
        log.info("Setting environment properties");
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
        dotenv.entries().forEach(entry -> {
            log.info("Key: {}, Value: {}", entry.getKey(), entry.getValue());
            System.setProperty(entry.getKey(), entry.getValue());
        });
        log.info("Set environment properties");
    }
}
