package br.com.fiap.stopcar.application.annotations;

import br.com.fiap.stopcar.application.exceptions.AppException;
import br.com.fiap.stopcar.application.exceptions.UnexpectException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AppErrorProcessor {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AppErrorProcessor.class);

    @Around(value = "@annotation(br.com.fiap.stopcar.application.annotations.AppError)")
    public Object handle(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (Exception e) {
            if (e instanceof AppException) {
                throw e;
            }
            log.error(e.getMessage());
            throw new UnexpectException(e);
        }
    }
}
