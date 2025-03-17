package com.skbroadband.doms.global.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.config
 * @File : DatabaseConfig
 * @Program :
 * @Date : 2022-11-17
 * @Comment :
 */
@Setter
@Configuration
@EnableJpaRepositories(basePackages = {"com.skbroadband.doms.web.repository",
        "com.skbroadband.doms.global.component.security.auth"},
        entityManagerFactoryRef = "webEntityManagerFactory",
        transactionManagerRef = "webTransactionManager")
@ConfigurationProperties(prefix = "datasource.web")
public class WebDataSourceConfig {
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;
    @Value("${spring.jpa.database-platform}")
    private String dialect;
    @Value("${spring.jpa.properties.hibernate.show-sql}")
    private boolean showSql;
    @Value("${spring.jpa.properties.hibernate.format_sql}")
    private boolean formatSql;
    @Value("${spring.jpa.properties.hibernate.default_batch_fetch_size}")
    private int defaultBatchFetchSize;

    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private int maximumPoolSize;
    private int minimumIdle;
    private long maxLifetime;
    private long idleTimeout;

    @Primary
    @Bean(name = "webDataSource")
    public DataSource webDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        hikariDataSource.setMaximumPoolSize(maximumPoolSize);
        hikariDataSource.setMinimumIdle(minimumIdle);
        hikariDataSource.setMaxLifetime(maxLifetime);
        hikariDataSource.setIdleTimeout(idleTimeout);

        return hikariDataSource;
    }

    @Primary
    @Bean(name = "webEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean webEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(webDataSource());

        // Entity Package 경로
        em.setPackagesToScan("com.skbroadband.doms.web.entity");
        em.setPersistenceUnitName("web");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        // Hibernate 설정
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", ddlAuto);
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.show_sql", showSql);
        properties.put("hibernate.format_sql", formatSql);
        properties.put("hibernate.default_batch_fetch_size", defaultBatchFetchSize);

        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean(name = "webTransactionManager")
    public PlatformTransactionManager webTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(webEntityManagerFactory().getObject());

        return transactionManager;
    }
}
