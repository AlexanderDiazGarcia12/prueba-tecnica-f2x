package com.f2x.pruebatecnica.entidad_financiera.service.transaccion;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.ProductoDto;

import java.math.BigDecimal;

public class Transferencia implements EjecutorTransaccion {
    @Override
    public void ejecutar(ProductoDto productoOrigen, ProductoDto productoDestino, BigDecimal monto) {
        productoOrigen.setSaldo(BigDecimal.valueOf(productoOrigen.getSaldo()).subtract(monto).doubleValue());
        productoDestino.setSaldo(BigDecimal.valueOf(productoDestino.getSaldo()).add(monto).doubleValue());
    }
}
