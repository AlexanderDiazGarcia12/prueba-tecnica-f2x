package com.f2x.pruebatecnica.entidad_financiera.controller;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.ClienteDto;
import com.f2x.pruebatecnica.entidad_financiera.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ClienteControllerTest {
    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDadoQueExistenClientesCuandoSeSolicitanTodosLosClientesEntoncesDevuelveListaDeClientesYEstado200OK() {
        // Arrange
        List<ClienteDto> expectedClients = List.of(new ClienteDto(), new ClienteDto());
        when(clienteService.obtenerTodosLosClientes()).thenReturn(expectedClients);

        // Act
        ResponseEntity<List<ClienteDto>> response = clienteController.clientesGet();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedClients, response.getBody());
    }

    @Test
    void testClietestDadoUnIdDeClienteValidoCuandoSeEliminaElClienteEntoncesDevuelveEstado200OKntesIdDeleteResultadoExitoso() {
        // Arrange
        Long clientId = 1L;

        // Act
        ResponseEntity<Void> response = clienteController.clientesIdDelete(clientId);

        // Assert
        verify(clienteService).eliminarCliente(clientId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDadoUnIdDeClienteInexistenteCuandoSeIntentaEliminarElClienteEntoncesLanzaExcepcionDeEntidadNoEncontrada() {
        // Arrange
        Long clientId = 999L;
        doThrow(new EntityNotFoundException("Client not found")).when(clienteService).eliminarCliente(clientId);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            clienteController.clientesIdDelete(clientId);
        });
        assertEquals("Client not found", exception.getMessage());
    }

    @Test
    void testDadoUnIdDeClienteInexistenteCuandoSeConsultaElClienteEntoncesLanzaExcepcionDeEntidadNoEncontrada() {
        // Arrange
        Long clientId = 999L;
        when(clienteService.obtenerClientePorId(clientId)).thenThrow(new EntityNotFoundException("Client not found"));

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            clienteController.clientesIdGet(clientId);
        });
        assertEquals("Client not found", exception.getMessage());
    }

    @Test
    void testClientesIdPtestDadoUnIdDeClienteInexistenteCuandoSeIntentaActualizarElClienteEntoncesLanzaExcepcionDeEntidadNoEncontradautCuandoElClienteNoExiste() {
        // Arrange
        Long clientId = 999L;
        ClienteDto updatedClient = new ClienteDto();
        when(clienteService.actualizarCliente(eq(clientId), any(ClienteDto.class))).thenThrow(new EntityNotFoundException("Client not found"));

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            clienteController.clientesIdPut(clientId, updatedClient);
        });
        assertEquals("Client not found", exception.getMessage());
    }
}