<<<<<<< HEAD
package com.example.library.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
=======
// LogExecutionTime.java
package com.example.library.aspect;

import java.lang.annotation.ElementType;
>>>>>>> e3423756a8e62ebe3ac874303a845b3039def7c5
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
<<<<<<< HEAD
@Inherited
public @interface LogExecutionTime {
=======
public @interface LogExecutionTime {
    String value() default "";
>>>>>>> e3423756a8e62ebe3ac874303a845b3039def7c5
}
