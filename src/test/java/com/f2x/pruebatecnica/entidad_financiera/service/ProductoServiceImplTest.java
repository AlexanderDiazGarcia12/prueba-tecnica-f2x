package com.f2x.pruebatecnica.entidad_financiera.service;

import com.f2x.pruebatecnica.entidad_financiera.model.constant.EstadoCuenta;
import com.f2x.pruebatecnica.entidad_financiera.model.dto.ClienteDto;
import com.f2x.pruebatecnica.entidad_financiera.model.dto.ProductoDto;
import com.f2x.pruebatecnica.entidad_financiera.model.entity.Producto;
import com.f2x.pruebatecnica.entidad_financiera.repository.ClienteRepository;
import com.f2x.pruebatecnica.entidad_financiera.repository.ProductoRepository;
import com.f2x.pruebatecnica.entidad_financiera.util.mapper.ProductoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductoServiceImplTest {
    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProductoMapper productoMapper;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void obtenerTodosLosProductosDebeRetornarListaDeProductosExitosamente() {
        // Arrange
        Producto producto = new Producto();
        ProductoDto productoDto = new ProductoDto();
        List<Producto> productos = List.of(producto);
        List<ProductoDto> productosDto = List.of(productoDto);

        when(productoRepository.findAll()).thenReturn(productos);
        when(productoMapper.productoToProductoDto(producto)).thenReturn(productoDto);

        // Act
        List<ProductoDto> result = productoService.obtenerTodosLosProductos();

        // Assert
        assertEquals(productosDto, result);
    }

    @Test
    public void manejarExcepcionDelRepositorioDebeLanzarRuntimeException() {
        // Arrange
        when(productoRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            productoService.obtenerTodosLosProductos();
        });
    }

    @Test
    public void obtenerProductoPorIdExistenteDebeRetornarProducto() {
        // Arrange
        Long productId = 1L;
        Producto producto = new Producto();
        producto.setId(productId);
        ProductoDto productoDto = new ProductoDto();
        productoDto.setId(productId);

        when(productoRepository.findById(productId)).thenReturn(Optional.of(producto));
        when(productoMapper.productoToProductoDto(producto)).thenReturn(productoDto);

        // Act
        ProductoDto result = productoService.obtenerProductoPorId(productId);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getId());
    }

    @Test
    public void obtenerProductoPorIdInexistenteDebeLanzarExcepcion() {
        // Arrange
        Long productId = 999L;

        when(productoRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            productoService.obtenerProductoPorId(productId);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
    }

    @Test
    public void crearProductoConDatosValidosDebeRetornarProductoCreado() {
        // Arrange
        ProductoDto productoDto = new ProductoDto();
        productoDto.setNumeroCuenta("1234567890");
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setId(1L);
        productoDto.setCliente(clienteDto);

        Producto producto = new Producto();
        when(productoRepository.existsByNumeroCuenta("1234567890")).thenReturn(false);
        when(clienteRepository.existsById(1L)).thenReturn(true);
        when(productoMapper.productoDtoToProducto(productoDto)).thenReturn(producto);
        when(productoRepository.save(producto)).thenReturn(producto);
        when(productoMapper.productoToProductoDto(producto)).thenReturn(productoDto);

        // Act
        ProductoDto result = productoService.crearProducto(productoDto);

        // Assert
        assertNotNull(result);
        assertEquals("1234567890", result.getNumeroCuenta());
    }

    @Test
    public void crearProductoConNumeroCuentaExistenteDebeLanzarExcepcion() {
        // Arrange
        ProductoDto productoDto = new ProductoDto();
        productoDto.setNumeroCuenta("1234567890");
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setId(1L);
        productoDto.setCliente(clienteDto);

        when(productoRepository.existsByNumeroCuenta("1234567890")).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            productoService.crearProducto(productoDto);
        });
    }

    @Test
    public void actualizarProductoConDatosValidosDebeRetornarProductoActualizado() {
        // Arrange
        Long id = 1L;
        Producto producto = new Producto();
        producto.setId(id);
        producto.setEstado(EstadoCuenta.ACTIVA);
        producto.setSaldo(1000.0);
        producto.setExentaGmf(false);

        ProductoDto productoActualizado = new ProductoDto();
        productoActualizado.setEstado(EstadoCuenta.INACTIVA);
        productoActualizado.setSaldo(2000.0);
        productoActualizado.setExentaGmf(true);

        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        when(productoMapper.productoToProductoDto(any(Producto.class))).thenReturn(productoActualizado);

        // Act
        ProductoDto result = productoService.actualizarProducto(id, productoActualizado);

        // Assert
        assertNotNull(result);
        assertEquals(EstadoCuenta.INACTIVA, result.getEstado());
        assertEquals(2000.0, result.getSaldo());
        assertTrue(result.getExentaGmf());
    }

    @Test
    public void actualizarProductoNoEncontradoDebeLanzarExcepcion() {
        // Arrange
        Long id = 1L;
        ProductoDto productoActualizado = new ProductoDto();

        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            productoService.actualizarProducto(id, productoActualizado);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
    }

    @Test
    public void eliminarProductoConSaldoCeroDebeEliminarProducto() {
        // Arrange
        Producto producto = new Producto();
        producto.setSaldo(0.0);
        ProductoDto productoDto = ProductoDto.builder().saldo(0.0).build();
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoMapper.productoToProductoDto(any(Producto.class))).thenReturn(productoDto);
        // Act
        productoService.eliminarProducto(1L);

        // Assert
        verify(productoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void eliminarProductoConSaldoNoCeroDebeLanzarExcepcion() {
        // Arrange
        Producto producto = new Producto();
        producto.setSaldo(100.0);
        ProductoDto productoDto = ProductoDto.builder().saldo(100.0).build();
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoMapper.productoToProductoDto(any(Producto.class))).thenReturn(productoDto);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            productoService.eliminarProducto(1L);
        });
    }
}