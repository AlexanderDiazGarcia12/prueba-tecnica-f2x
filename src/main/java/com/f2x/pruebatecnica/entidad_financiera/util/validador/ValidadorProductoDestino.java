package com.f2x.pruebatecnica.entidad_financiera.util.validador;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorProductoDestinoImpl.class)
@Documented
public @interface ValidadorProductoDestino {
    String message() default "El producto de destino es obligatorio para transferencias";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
