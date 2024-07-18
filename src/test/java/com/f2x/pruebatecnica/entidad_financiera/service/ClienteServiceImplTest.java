package com.f2x.pruebatecnica.entidad_financiera.service;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.ClienteDto;
import com.f2x.pruebatecnica.entidad_financiera.model.entity.Cliente;
import com.f2x.pruebatecnica.entidad_financiera.repository.ClienteRepository;
import com.f2x.pruebatecnica.entidad_financiera.util.mapper.ClienteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void obtenerTodosLosClientesDebeRetornarListaDeClientesExitosamente() {
        // Arrange
        List<Cliente> clientes = List.of(new Cliente(), new Cliente());
        when(clienteRepository.findAll()).thenReturn(clientes);
        when(clienteMapper.clienteToClienteDto(any(Cliente.class))).thenReturn(new ClienteDto());

        // Act
        List<ClienteDto> result = clienteService.obtenerTodosLosClientes();

        // Assert
        assertEquals(2, result.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    public void obtenerClientePorIdDebeRetornarClienteExitosamente() {
        // Arrange
        Cliente cliente = new Cliente();
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteMapper.clienteToClienteDto(cliente)).thenReturn(new ClienteDto());

        // Act
        ClienteDto result = clienteService.obtenerClientePorId(1L);

        // Assert
        assertNotNull(result);
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    public void crearNuevoClienteDebeSerExitosoConCorreoElectronicoUnico() {
        // Arrange
        ClienteDto clienteDto = new ClienteDto();
        Cliente cliente = new Cliente();
        when(clienteRepository.existsByCorreoElectronico(anyString())).thenReturn(false);
        when(clienteMapper.clienteDtoToCliente(clienteDto)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(clienteMapper.clienteToClienteDto(cliente)).thenReturn(clienteDto);

        // Act
        ClienteDto result = clienteService.crearCliente(clienteDto);

        // Assert
        assertNotNull(result);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void actualizarClienteExistenteDebeSerExitoso() {
        // Arrange
        Cliente cliente = new Cliente();
        ClienteDto clienteActualizado = new ClienteDto();
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(clienteMapper.clienteToClienteDto(cliente)).thenReturn(clienteActualizado);

        // Act
        ClienteDto result = clienteService.actualizarCliente(1L, clienteActualizado);

        // Assert
        assertNotNull(result);
        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void eliminarClienteDebeSerExitosoSinProductosVinculados() {
        // Arrange
        when(clienteRepository.existsByIdAndProductosIsNotEmpty(1L)).thenReturn(false);

        // Act
        clienteService.eliminarCliente(1L);

        // Assert
        verify(clienteRepository, times(1)).existsByIdAndProductosIsNotEmpty(1L);
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    public void obtenerClienteConIdInexistenteDebeLanzarExcepcion() {
        // Arrange
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> clienteService.obtenerClientePorId(1L));
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    public void actualizarClienteConIdInexistenteDebeLanzarExcepcion() {
        // Arrange
        ClienteDto clienteActualizado = new ClienteDto();
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> clienteService.actualizarCliente(1L, clienteActualizado));
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    public void eliminarClienteConProductosVinculadosDebeLanzarExcepcion() {
        // Arrange
        when(clienteRepository.existsByIdAndProductosIsNotEmpty(1L)).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> clienteService.eliminarCliente(1L));
        verify(clienteRepository, times(1)).existsByIdAndProductosIsNotEmpty(1L);
    }

}