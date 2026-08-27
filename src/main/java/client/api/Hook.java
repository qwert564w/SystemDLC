package client.api;

import client.enums.InjectPoint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Repeatable(Hooks.class)
public @interface Hook {
   public Class target() default void.class;

   public String method() default "";

   public InjectPoint getInjectPoint();

   public String desc() default "";

   public String targetName() default "";

   public int methodIndex() default -1;
}
