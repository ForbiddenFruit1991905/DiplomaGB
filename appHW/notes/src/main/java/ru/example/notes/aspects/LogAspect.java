package ru.example.notes.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Component
@Aspect
public class LogAspect {

    private Logger logger = Logger.getLogger(LogAspect.class.getName());

    public LogAspect() {
        try {
            FileHandler fileHandler = new FileHandler("myLogAspect.log", true);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "This is an error in FileHandler.", e);
        }
    }

    @Around("execution(* ru.example.hw.service.impl.*.*(..))")
    @Order(1)
    public void log(JoinPoint joinPoint, Object returnedValue){
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        Object [] arguments = joinPoint.getArgs();
        System.out.println("Method " + methodName + " with parameters " + Arrays.asList(arguments) + " will execute");
        System.out.println("Class " + className + " with parameters " + Arrays.asList(arguments) + " will execute");

    }

    @AfterReturning(value = "@annotation(TrackUserAction)", returning = "returnedValue")
    public void logMethodExecution(JoinPoint joinPoint, Object returnedValue) {
        // Получение информации о методе
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        Object[] methodArgs = joinPoint.getArgs();

        // Цвета для логирования
        String ANSI_RESET = "u001B[0m";         // Сброс цвета
        String myColorStart = "u001B[43;30m";  // Желтый фон с черным текстом
        String myColor1 = "u001B[32m";         // Зеленый
        String myColor2 = "u001B[35m";         // Фиолетовый
        String myColor3 = "u001B[36m";         // Циановый
        String myColor4 = "u001B[34m";         // Синий
        String myColorEND = "u001B[43;30m";    // Закрытие цветного блока (желтый фон с черным текстом)

        // Вывод информации в консоль в виде лога
        logger.info(myColorStart + "Логирование выполнения метода: " + ANSI_RESET);
        logger.info(myColor1 + "Вызванный метод: " + ANSI_RESET + methodName);
        logger.info(myColor2 + "Класс: " + ANSI_RESET + className);
        logger.info(myColor3 + "Аргументы: " + ANSI_RESET + Arrays.toString(methodArgs));
        logger.info(myColor4 + "Метод выполнен и вернул результат: " + ANSI_RESET + returnedValue);
        logger.info(myColorEND + "Конец блока логирования. " + ANSI_RESET);
    }

    @Around("@annotation(TrackUserAction)")
    public Object callLog(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Вызов метода: " + joinPoint.getSignature().getName()
                + ", с аргументами: " + Arrays.toString(joinPoint.getArgs()));
        return joinPoint.proceed();
    }

    @Around("execution(* ru.example.hw.service.impl.*.*(..))")
    @Order(2)
    public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("Метод: " + joinPoint.getSignature().getName() + " - " + elapsedTime + " милсек");
        return result;
    }

    @AfterReturning(value = "@annotation(TrackUserAction)", returning = "returnedValue")
    public void log(Object returnedValue){
        logger.info("Method executed and returned " + returnedValue);
    }
    
}
