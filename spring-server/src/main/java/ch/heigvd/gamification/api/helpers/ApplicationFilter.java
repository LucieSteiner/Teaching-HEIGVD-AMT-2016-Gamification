package ch.heigvd.gamification.api.helpers;

import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 *
 * @author Lucie Steiner
 */
@Target(value=METHOD)
@Retention(value=RUNTIME)
public @interface ApplicationFilter {
   
}
