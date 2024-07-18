package com.f2x.pruebatecnica.entidad_financiera.service;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.ClienteDto;

import java.util.List;

public interface ClienteService {
    List<ClienteDto> obtenerTodosLosClientes();
    ClienteDto obtenerClientePorId(Long id);
    ClienteDto crearCliente(ClienteDto cliente);
    ClienteDto actualizarCliente(Long id, ClienteDto clienteActualizado);
    void eliminarCliente(Long id);
}
