package com.f2x.pruebatecnica.entidad_financiera.util.validador;

import com.f2x.pruebatecnica.entidad_financiera.model.constant.TipoTransaccion;
import com.f2x.pruebatecnica.entidad_financiera.model.dto.TransaccionDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorProductoDestinoImpl implements ConstraintValidator<ValidadorProductoDestino, TransaccionDto> {
    @Override
    public boolean isValid(TransaccionDto transaccionDto, ConstraintValidatorContext context) {
        if (transaccionDto.getTipoTransaccion() == TipoTransaccion.TRANSFERENCIA) {
            return transaccionDto.getProductoDestino() != null;
        }
        return true;
    }
}
