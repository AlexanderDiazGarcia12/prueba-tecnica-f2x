package com.f2x.pruebatecnica.entidad_financiera.controller;

import com.f2x.pruebatecnica.entidad_financiera.model.constant.TipoTransaccion;
import com.f2x.pruebatecnica.entidad_financiera.model.dto.TransaccionDto;
import com.f2x.pruebatecnica.entidad_financiera.service.TransaccionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransaccionControllerTest {

    @Mock
    private TransaccionService transaccionService;

    @InjectMocks
    private TransaccionController transaccionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDadoQueExistenTransaccionesCuandoSeSolicitanTodasLasTransaccionesEntoncesDevuelveListaDeTransaccionesYEstado200OK() {
        // Arrange
        List<TransaccionDto> expectedTransactions = List.of(new TransaccionDto(), new TransaccionDto());
        when(transaccionService.obtenerTodasLasTransacciones()).thenReturn(expectedTransactions);

        // Act
        ResponseEntity<List<TransaccionDto>> response = transaccionController.transaccionesGet();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTransactions, response.getBody());
    }

    @Test
    public void testDadoUnErrorEnElServicioCuandoSeSolicitanTodasLasTransaccionesEntoncesLanzaExcepcion() {
        // Arrange
        when(transaccionService.obtenerTodasLasTransacciones()).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            transaccionController.transaccionesGet();
        });
    }

    @Test
    public void testDadoUnIdDeTransaccionValidoCuandoSeEliminaLaTransaccionEntoncesDevuelveEstado200OK() {
        // Arrange
        Long validId = 1L;

        // Act
        ResponseEntity<Void> response = transaccionController.transaccionesIdDelete(validId);

        // Assert
        verify(transaccionService, times(1)).eliminarTransaccion(validId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteTransactionWithNonExistentIdShouldThrowRuntimeException() {
        // Arrange
        Long nonExistentId = 999L;
        doThrow(new RuntimeException("Transaction not found")).when(transaccionService).eliminarTransaccion(nonExistentId);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            transaccionController.transaccionesIdDelete(nonExistentId);
        });
        verify(transaccionService, times(1)).eliminarTransaccion(nonExistentId);
    }

    @Test
    public void obtenerTransaccionConIdValidoDebeRetornarEstadoOk() {
        // Arrange
        Long validId = 1L;
        TransaccionDto transaccionDto = new TransaccionDto();
        when(transaccionService.obtenerTransaccionPorId(validId)).thenReturn(transaccionDto);

        // Act
        ResponseEntity<TransaccionDto> response = transaccionController.transaccionesIdGet(validId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transaccionDto, response.getBody());
    }

    @Test
    public void obtenerTransaccionConIdInvalidoDebeLanzarExcepcionNoEncontrado() {
        // Arrange
        Long invalidId = 999L;
        when(transaccionService.obtenerTransaccionPorId(invalidId)).thenThrow(new RuntimeException("Not Found"));

        // Act
        ResponseEntity<TransaccionDto> response = null;
        try {
            response = transaccionController.transaccionesIdGet(invalidId);
        } catch (RuntimeException e) {
            // Assert
            assertEquals("Not Found", e.getMessage());
            assertNull(response);
        }
    }

    @Test
    public void actualizarTransaccionExistenteDebeRetornarTransaccionActualizada() {
        // Arrange
        Long validId = 1L;
        TransaccionDto transaccionDto = new TransaccionDto();
        transaccionDto.setId(validId);
        transaccionDto.setMonto(100.0);
        transaccionDto.setTipoTransaccion(TipoTransaccion.CONSIGNACION);
        when(transaccionService.actualizarTransaccion(validId, transaccionDto)).thenReturn(transaccionDto);

        // Act
        ResponseEntity<TransaccionDto> response = transaccionController.transaccionesIdPut(validId, transaccionDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transaccionDto, response.getBody());
    }

    @Test
    public void actualizarTransaccionInexistenteDebeLanzarExcepcion() {
        // Arrange
        Long nonExistentId = 999L;
        TransaccionDto transaccionDto = new TransaccionDto();
        transaccionDto.setId(nonExistentId);
        transaccionDto.setMonto(100.0);
        transaccionDto.setTipoTransaccion(TipoTransaccion.CONSIGNACION);
        when(transaccionService.actualizarTransaccion(nonExistentId, transaccionDto)).thenThrow(new RuntimeException("Transaction not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            transaccionController.transaccionesIdPut(nonExistentId, transaccionDto);
        });
    }

    @Test
    public void crearNuevaTransaccionConDatosValidosDebeRetornarTransaccionCreada() {
        // Arrange
        TransaccionDto transaccionDto = new TransaccionDto();
        transaccionDto.setId(1L);
        transaccionDto.setMonto(100.0);
        when(transaccionService.crearTransaccion(any(TransaccionDto.class))).thenReturn(transaccionDto);

        // Act
        ResponseEntity<TransaccionDto> response = transaccionController.transaccionesPost(transaccionDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }
}