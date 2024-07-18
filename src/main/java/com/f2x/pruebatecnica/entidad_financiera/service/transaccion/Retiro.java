package com.f2x.pruebatecnica.entidad_financiera.service.transaccion;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.ProductoDto;

import java.math.BigDecimal;

public class Retiro implements EjecutorTransaccion {
    @Override
    public void ejecutar(ProductoDto productoOrigen, ProductoDto productoDestino, BigDecimal monto) {
        BigDecimal saldo = BigDecimal.valueOf(productoOrigen.getSaldo());
        if (saldo.compareTo(monto) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
        productoOrigen.setSaldo(saldo.subtract(monto).doubleValue());
    }
}
