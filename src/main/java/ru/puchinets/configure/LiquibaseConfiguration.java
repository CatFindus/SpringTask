package ru.puchinets.configure;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfiguration {

    @Bean
    @Profile("!test&&!webtest")
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("db/changelog.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

}
