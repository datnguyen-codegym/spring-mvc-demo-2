package codegym.vn.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {

    @Around(value = "executeBeforeAPI()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("logging before execute API");
        Object result = joinPoint.proceed();
        System.out.println("logging after execute API");
        return result;
    }

    @Pointcut(value = "within(codegym.vn.controller.*)")
    public void executeBeforeAPI() {

    }
}
