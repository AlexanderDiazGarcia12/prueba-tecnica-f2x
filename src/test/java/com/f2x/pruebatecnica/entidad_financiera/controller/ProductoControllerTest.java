package com.f2x.pruebatecnica.entidad_financiera.controller;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.ProductoDto;
import com.f2x.pruebatecnica.entidad_financiera.service.ProductoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoControllerTest {
    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDadoQueExistenProductosCuandoSeSolicitanTodosLosProductosEntoncesDevuelveEstado200OK() {
        // Arrange
        List<ProductoDto> productos = new ArrayList<>();
        when(productoService.obtenerTodosLosProductos()).thenReturn(productos);

        // Act
        ResponseEntity<List<ProductoDto>> response = productoController.productosGet();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productos, response.getBody());
    }

    @Test
    public void testDadoQueNoExistenProductosCuandoSeSolicitanTodosLosProductosEntoncesDevuelveListaVacia() {
        // Arrange
        when(productoService.obtenerTodosLosProductos()).thenReturn(null);

        // Act
        ResponseEntity<List<ProductoDto>> response = productoController.productosGet();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDadoUnIdDeProductoValidoCuandoSeEliminaElProductoEntoncesDevuelveEstado200OK() {
        // Arrange
        Long validId = 1L;

        // Act
        ResponseEntity<Void> response = productoController.productosIdDelete(validId);

        // Assert
        verify(productoService, times(1)).eliminarProducto(validId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDadoUnIdDeProductoInexistenteCuandoSeIntentaEliminarElProductoEntoncesLanzaExcepcionDeEntidadNoEncontrada() {
        // Arrange
        Long nonExistentId = 999L;
        doThrow(new EntityNotFoundException("Product not found")).when(productoService).eliminarProducto(nonExistentId);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            productoController.productosIdDelete(nonExistentId);
        });

        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    public void testDadoUnIdDeProductoValidoCuandoSeConsultaElProductoEntoncesDevuelveElProductoYEstado200OK() {
        // Arrange
        Long validId = 1L;
        ProductoDto expectedProducto = new ProductoDto();
        when(productoService.obtenerProductoPorId(validId)).thenReturn(expectedProducto);

        // Act
        ResponseEntity<ProductoDto> response = productoController.productosIdGet(validId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProducto, response.getBody());
    }

    @Test
    public void testDadoUnIdDeProductoInexistenteCuandoSeConsultaElProductoEntoncesDevuelveNull() {
        // Arrange
        Long nonExistentId = 999L;
        when(productoService.obtenerProductoPorId(nonExistentId)).thenReturn(null);

        // Act
        ResponseEntity<ProductoDto> response = productoController.productosIdGet(nonExistentId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDadoUnProductoExistenteYDatosValidosCuandoSeActualizaElProductoEntoncesDevuelveElProductoActualizadoYEstado200OK() {
        // Arrange
        Long validId = 1L;
        ProductoDto validBody = new ProductoDto();
        validBody.setId(validId);
        validBody.setNumeroCuenta("1234567890");
        validBody.setSaldo(1000.0);
        when(productoService.actualizarProducto(validId, validBody)).thenReturn(validBody);

        // Act
        ResponseEntity<ProductoDto> response = productoController.productosIdPut(validId, validBody);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(validBody, response.getBody());
    }

    @Test
    public void testDadoUnIdDeProductoInexistenteCuandoSeIntentaActualizarElProductoEntoncesLanzaExcepcionDeEntidadNoEncontrada() {
        // Arrange
        Long nonExistentId = 999L;
        ProductoDto body = new ProductoDto();
        body.setId(nonExistentId);
        body.setNumeroCuenta("1234567890");
        body.setSaldo(1000.0);
        when(productoService.actualizarProducto(nonExistentId, body)).thenThrow(new EntityNotFoundException("Product not found"));

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            productoController.productosIdPut(nonExistentId, body);
        });
    }
}