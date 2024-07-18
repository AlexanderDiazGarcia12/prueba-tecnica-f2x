package com.f2x.pruebatecnica.entidad_financiera.service;

import com.f2x.pruebatecnica.entidad_financiera.model.constant.TipoTransaccion;
import com.f2x.pruebatecnica.entidad_financiera.model.dto.ProductoDto;
import com.f2x.pruebatecnica.entidad_financiera.model.dto.TransaccionDto;
import com.f2x.pruebatecnica.entidad_financiera.model.entity.Transaccion;
import com.f2x.pruebatecnica.entidad_financiera.repository.TransaccionRepository;
import com.f2x.pruebatecnica.entidad_financiera.service.transaccion.TransaccionFactory;
import com.f2x.pruebatecnica.entidad_financiera.util.mapper.TransaccionMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransaccionServiceImplTest {

    @Mock
    private ProductoService productoService;

    @Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private TransaccionMapper transaccionMapper;

    @Mock
    private TransaccionFactory transaccionFactory;

    @InjectMocks
    private TransaccionServiceImpl transaccionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deberiaRecuperarTodasLasTransaccionesExitosamente() {
        // Arrange
        Transaccion transaccion = new Transaccion();
        List<Transaccion> transacciones = List.of(transaccion);
        when(transaccionRepository.findAll()).thenReturn(transacciones);
        TransaccionDto transaccionDto = new TransaccionDto();
        when(transaccionMapper.transaccionToTransaccionDto(transaccion)).thenReturn(transaccionDto);

        // Act
        List<TransaccionDto> result = transaccionService.obtenerTodasLasTransacciones();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(transaccionDto, result.get(0));
    }

    @Test
    public void deberiaManejarValoresNulosEnEntidadesDeTransaccion() {
        // Arrange
        List<Transaccion> transacciones = List.of();
        when(transaccionRepository.findAll()).thenReturn(transacciones);
        when(transaccionMapper.transaccionToTransaccionDto(null)).thenReturn(null);

        // Act
        List<TransaccionDto> result = transaccionService.obtenerTodasLasTransacciones();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void deberiaRecuperarTransaccionConIdValido() {
        // Arrange
        Long validId = 1L;
        Transaccion transaccion = new Transaccion();
        TransaccionDto transaccionDto = new TransaccionDto();

        when(transaccionRepository.findById(validId)).thenReturn(Optional.of(transaccion));
        when(transaccionMapper.transaccionToTransaccionDto(transaccion)).thenReturn(transaccionDto);

        // Act
        TransaccionDto result = transaccionService.obtenerTransaccionPorId(validId);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(transaccionDto, result);
    }

    @Test
    public void deberiaCrearTransaccionExito() {
        // Arrange
        TransaccionDto transaccionDto = new TransaccionDto();
        transaccionDto.setTipoTransaccion(TipoTransaccion.CONSIGNACION);
        transaccionDto.setMonto(100.0);
        ProductoDto productoOrigen = new ProductoDto();
        productoOrigen.setId(1L);
        transaccionDto.setProductoOrigen(productoOrigen);

        when(productoService.obtenerProductoPorId(1L)).thenReturn(productoOrigen);
        when(transaccionFactory.crearTransaccion(TipoTransaccion.CONSIGNACION)).thenReturn((productoOrigen1, productoDestino, monto) -> {
        });
        when(transaccionMapper.transaccionDtoToTransaccion(transaccionDto)).thenReturn(new Transaccion());
        when(transaccionMapper.transaccionToTransaccionDto(any(Transaccion.class))).thenReturn(transaccionDto);
        when(transaccionRepository.save(any(Transaccion.class))).thenReturn(new Transaccion());

        // Act
        TransaccionDto result = transaccionService.crearTransaccion(transaccionDto);

        // Assert
        assertNotNull(result);
        assertEquals(transaccionDto.getMonto(), result.getMonto());
    }

    @Test
    public void deberiaCrearTransaccionConIdProductoOrigenInvalido() {
        // Arrange
        TransaccionServiceImpl transaccionService = new TransaccionServiceImpl(productoService, transaccionRepository, transaccionMapper, transaccionFactory);
        TransaccionDto transaccionDto = new TransaccionDto();
        transaccionDto.setTipoTransaccion(TipoTransaccion.CONSIGNACION);
        transaccionDto.setMonto(100.0);
        ProductoDto productoOrigen = new ProductoDto();
        productoOrigen.setId(999L); // Invalid ID
        transaccionDto.setProductoOrigen(productoOrigen);

        when(productoService.obtenerProductoPorId(999L)).thenThrow(new RuntimeException("Producto no encontrado"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            transaccionService.crearTransaccion(transaccionDto);
        });
    }

    @Test
    public void deberiaActualizarTransaccionExito() {
        // Arrange
        Long transaccionId = 1L;
        TransaccionDto transaccionActualizada = new TransaccionDto();
        transaccionActualizada.setMonto(200.0);
        transaccionActualizada.setDescripcion("Descripción actualizada");

        Transaccion transaccion = new Transaccion();
        transaccion.setId(transaccionId);
        transaccion.setMonto(100.0);
        transaccion.setDescripcion("Descripción antigua");

        when(transaccionRepository.findById(transaccionId)).thenReturn(Optional.of(transaccion));
        when(transaccionRepository.save(any(Transaccion.class))).thenReturn(transaccion);
        when(transaccionMapper.transaccionToTransaccionDto(any(Transaccion.class))).thenReturn(transaccionActualizada);

        TransaccionServiceImpl transaccionService = new TransaccionServiceImpl(null, transaccionRepository, transaccionMapper, null);

        // Act
        TransaccionDto result = transaccionService.actualizarTransaccion(transaccionId, transaccionActualizada);

        // Assert
        assertEquals(transaccionActualizada.getMonto(), result.getMonto());
        assertEquals(transaccionActualizada.getDescripcion(), result.getDescripcion());
    }

    @Test
    public void deberiaLanzarExcepcionSiTransaccionNoSeEncuentra() {
        // Arrange
        Long transaccionId = 1L;
        TransaccionDto transaccionActualizada = new TransaccionDto();
        transaccionActualizada.setMonto(200.0);
        transaccionActualizada.setDescripcion("Descripción actualizada");

        when(transaccionRepository.findById(transaccionId)).thenReturn(Optional.empty());

        TransaccionServiceImpl transaccionService = new TransaccionServiceImpl(null, transaccionRepository, null, null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            transaccionService.actualizarTransaccion(transaccionId, transaccionActualizada);
        });
    }
}