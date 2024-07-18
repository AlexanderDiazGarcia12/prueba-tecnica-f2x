package com.f2x.pruebatecnica.entidad_financiera.service;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.TransaccionDto;

import java.util.List;

public interface TransaccionService {
    List<TransaccionDto> obtenerTodasLasTransacciones();
    TransaccionDto obtenerTransaccionPorId(Long id) throws RuntimeException;
    TransaccionDto crearTransaccion(TransaccionDto transaccionDto) throws RuntimeException;
    TransaccionDto actualizarTransaccion(Long id, TransaccionDto transaccionActualizada) throws RuntimeException;
    void eliminarTransaccion(Long id) throws RuntimeException;
}
