package ru.puchinets.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("isServiceLayer() || isRepositoryLayer() || isControllerLayer()")
    public void addBeforeLogging(JoinPoint joinPoint) {
        log.trace("Начало выполнения метода класс: {}, метод: {}, аргументы: {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), Arrays.stream(joinPoint.getArgs()).toList());
    }

    @AfterReturning(value = "isServiceLayer() || isRepositoryLayer() || isControllerLayer()", returning = "result")
    public void addAfterLogging(JoinPoint joinPoint, Object result) {
        log.trace("Окончание выполнения метода, метод: {}, результат: {}", joinPoint.getSignature().getDeclaringTypeName(), result);
    }

    @AfterThrowing(value = "isServiceLayer() || isRepositoryLayer() || isControllerLayer()", throwing = "ex")
    public void addAfterExceptionLogging(JoinPoint joinPoint, Throwable ex) {
        log.warn("Окончание выполнения метода класс c исключением: метод: {}, исключение: {}", joinPoint.getSignature().getDeclaringTypeName(), ex.getMessage());
    }

    @Pointcut("within(ru.puchinets.repository.*)")
    public void isRepositoryLayer() {

    }

    @Pointcut("within(ru.puchinets.service.*)")
    public void isServiceLayer() {

    }

    @Pointcut("this(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {

    }
}
