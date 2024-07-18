package com.f2x.pruebatecnica.entidad_financiera.service.transaccion;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.ProductoDto;

import java.math.BigDecimal;

public class Consignacion implements EjecutorTransaccion {
    @Override
    public void ejecutar(ProductoDto productoOrigen, ProductoDto productoDestino, BigDecimal monto) {
        productoOrigen.setSaldo(BigDecimal.valueOf(productoOrigen.getSaldo()).add(monto).doubleValue());
    }
}
