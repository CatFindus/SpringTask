package ru.puchinets.annotation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.puchinets.TestConfiguration;
import ru.puchinets.configure.DataBaseConfiguration;
import ru.puchinets.configure.WebConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringJUnitConfig
@ContextConfiguration(classes = { WebConfiguration.class, DataBaseConfiguration.class, TestConfiguration.class})
@ActiveProfiles("webtest")
@TestPropertySource("classpath:database-tests.properties")
@Testcontainers
public @interface ITController {
}
