package ru.puchinets;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;


@Configuration
//@ComponentScan(basePackages = "ru.puchinets")
//@Import(value = { DataBaseConfiguration.class })

public class TestConfiguration {

    @Bean
    @Primary
    public PostgreSQLContainer<?> postgreSQLContainer() {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.3");
        container.start();
        return container;
    }

    @Bean
    public DataSource dataSource(PostgreSQLContainer<?> postgreSQLContainer) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(postgreSQLContainer.getJdbcUrl());
        dataSource.setUsername(postgreSQLContainer.getUsername());
        dataSource.setPassword(postgreSQLContainer.getPassword());
        return dataSource;
    }

    @Bean
    public SpringLiquibase liquibaseTest(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("db/changelog-test.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}
