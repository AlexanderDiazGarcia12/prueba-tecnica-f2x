package com.f2x.pruebatecnica.entidad_financiera.controller;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.ClienteDto;
import com.f2x.pruebatecnica.entidad_financiera.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class ClienteController implements ClienteApi {

    private final ClienteService clienteService;

    @Override
    public ResponseEntity<List<ClienteDto>> clientesGet() {
        return ResponseEntity.ok(clienteService.obtenerTodosLosClientes());
    }

    @Override
    public ResponseEntity<Void> clientesIdDelete(Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ClienteDto> clientesIdGet(Long id) {
        return ResponseEntity.ok(clienteService.obtenerClientePorId(id));
    }

    @Override
    public ResponseEntity<ClienteDto> clientesIdPut(Long id, ClienteDto body) {
        return ResponseEntity.ok(clienteService.actualizarCliente(id, body));
    }

    @Override
    public ResponseEntity<ClienteDto> clientesPost(ClienteDto body) {
        return ResponseEntity.ok(clienteService.crearCliente(body));
    }
}
