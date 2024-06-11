package ru.puchinets.configure;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.puchinets.handler.GlobalExceptionHandler;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:database.properties")
@EnableJpaRepositories("ru.puchinets.repository")
@EnableTransactionManagement
//@Import({DataBaseConfiguration.class, LiquibaseConfiguration.class, WebConfiguration.class})
@ComponentScan(value = "ru.puchinets", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = org.springframework.stereotype.Controller.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = GlobalExceptionHandler.class)})
@EnableAspectJAutoProxy
public class DataBaseConfiguration {

    private final Environment environment;

    @Bean
    @Profile("!test&&!webtest")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("driver"));
        dataSource.setUrl(environment.getProperty("url"));
        dataSource.setUsername(environment.getProperty("dbuser"));
        dataSource.setPassword(environment.getProperty("dbpassword"));
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(true);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("ru.puchinets");
        factory.setDataSource(dataSource);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}
