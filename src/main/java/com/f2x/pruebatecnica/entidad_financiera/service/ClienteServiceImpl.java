package com.f2x.pruebatecnica.entidad_financiera.service;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.ClienteDto;
import com.f2x.pruebatecnica.entidad_financiera.model.entity.Cliente;
import com.f2x.pruebatecnica.entidad_financiera.repository.ClienteRepository;
import com.f2x.pruebatecnica.entidad_financiera.util.mapper.ClienteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    public List<ClienteDto> obtenerTodosLosClientes() {
        return clienteRepository.findAll().stream().map(clienteMapper::clienteToClienteDto).toList();
    }

    @Override
    public ClienteDto obtenerClientePorId(Long id) {
        return clienteRepository.findById(id).map(clienteMapper::clienteToClienteDto)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @Transactional
    @Override
    public ClienteDto crearCliente(ClienteDto cliente) {
        if (clienteRepository.existsByCorreoElectronico(cliente.getCorreoElectronico())) {
            throw new RuntimeException("El correo electrónico ya está en uso");
        }
        return clienteMapper.clienteToClienteDto(clienteRepository.save(
                clienteMapper.clienteDtoToCliente(cliente)));
    }

    @Transactional
    @Override
    public ClienteDto actualizarCliente(Long id, ClienteDto clienteActualizado) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->  new RuntimeException("Cliente no encontrado"));
        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setApellidos(clienteActualizado.getApellidos());
        cliente.setCorreoElectronico(clienteActualizado.getCorreoElectronico());
        return clienteMapper.clienteToClienteDto(clienteRepository.save(cliente));
    }

    @Transactional
    @Override
    public void eliminarCliente(Long id) {
        if (clienteRepository.existsByIdAndProductosIsNotEmpty(id)) {
            throw new RuntimeException("No se puede eliminar el cliente porque tiene productos vinculados");
        }
        clienteRepository.deleteById(id);
    }
}
