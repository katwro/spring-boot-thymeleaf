package mvc.som.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Value("${aop.logging.enabled:true}")
    private boolean loggingEnabled;

    @Pointcut("execution(* mvc.som.controller.*.*(..))")
    private void forControllerPackage() {
    }

    @Pointcut("execution(* mvc.som.repository.*.*(..))")
    private void forRepositoryPackage() {
    }

    @Pointcut("execution(* mvc.som.service.*.*(..))")
    private void forServicePackage() {
    }

    @Pointcut("forControllerPackage() || forRepositoryPackage() || forServicePackage()")
    private void forAppFlow() {
    }

    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint) {
        if (loggingEnabled) {
            String method = joinPoint.getSignature().toShortString();
            logger.info("@Before: calling method: " + method);

            Object[] args = joinPoint.getArgs();

            for (Object arg : args) {
                logger.info("==>>> argument: " + arg);
            }
        }
    }

    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        if (loggingEnabled) {
            String method = joinPoint.getSignature().toShortString();
            logger.info("@AfterReturning: from method: " + method);

            logger.info("==>>> result: " + result);
        }
    }

}
