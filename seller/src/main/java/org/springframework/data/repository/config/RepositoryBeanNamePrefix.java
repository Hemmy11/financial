package org.springframework.data.repository.config;

import java.lang.annotation.*;

/**
 * repository名字的前缀
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RepositoryBeanNamePrefix {
    String value();
}
