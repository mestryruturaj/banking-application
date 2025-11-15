package io.enscryptingbytes.banking_application.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    private static final String controllerExpression = "execution(* io.enscryptingbytes.banking_application.controller..*.*(..))";
    private static final String serviceExpression = "execution(* io.enscryptingbytes.banking_application.service..*.*(..))";
    private static final String repositoryExpression = "execution(* io.enscryptingbytes.banking_application.repository..*.*(..))";
    private static final String utilExpression = "execution(* io.enscryptingbytes.banking_application.util..*.*(..))";

    private static final String allLayerExpression = controllerExpression + " || " +
            serviceExpression + " || " +
            repositoryExpression + " || " +
            utilExpression;

    @Pointcut(allLayerExpression)
    private void allLayerExpressionPointCut() {

    }

    @Before("allLayerExpressionPointCut()")
    public void logBeforeExpression(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("[Before] Entering method call {}.{}() with arguments {}", className, methodName, args);
    }

    @After("allLayerExpressionPointCut()")
    public void logAfterExpression(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("[After] Completing method call {}.{}() with arguments {}", className, methodName, args);
    }
}
