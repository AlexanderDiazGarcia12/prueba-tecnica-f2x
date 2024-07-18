package com.f2x.pruebatecnica.entidad_financiera.service.transaccion;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.ProductoDto;

import java.math.BigDecimal;

public interface EjecutorTransaccion {
    void ejecutar(ProductoDto productoOrigen, ProductoDto productoDestino, BigDecimal monto);
}
