package com.f2x.pruebatecnica.entidad_financiera.controller;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.TransaccionDto;
import com.f2x.pruebatecnica.entidad_financiera.service.TransaccionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class TransaccionController implements TransaccionApi {

    private final TransaccionService transaccionService;

    @Override
    public ResponseEntity<List<TransaccionDto>> transaccionesGet() {
        return ResponseEntity.ok(transaccionService.obtenerTodasLasTransacciones());
    }

    @Override
    public ResponseEntity<Void> transaccionesIdDelete(Long id) {
        transaccionService.eliminarTransaccion(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TransaccionDto> transaccionesIdGet(Long id) {
        return ResponseEntity.ok(transaccionService.obtenerTransaccionPorId(id));
    }

    @Override
    public ResponseEntity<TransaccionDto> transaccionesIdPut(Long id, TransaccionDto body) {
        return ResponseEntity.ok(transaccionService.actualizarTransaccion(id, body));
    }

    @Override
    public ResponseEntity<TransaccionDto> transaccionesPost(TransaccionDto body) {
        return ResponseEntity.ok(transaccionService.crearTransaccion(body));
    }
}
