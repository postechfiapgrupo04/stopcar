package br.com.fiap.stopcar.util.caching.annotation;

import br.com.fiap.stopcar.util.caching.config.CachedAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({CachedAutoConfiguration.class})
@Documented
public @interface EnableMemcached {
}
