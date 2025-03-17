package com.skbroadband.doms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaAuditing
@EnableTransactionManagement
@EnableCaching
@SpringBootApplication
public class DomsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DomsApplication.class, args);
    }
}
