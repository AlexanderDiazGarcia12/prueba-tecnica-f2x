package com.f2x.pruebatecnica.entidad_financiera.service;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.ProductoDto;
import com.f2x.pruebatecnica.entidad_financiera.model.entity.Producto;
import com.f2x.pruebatecnica.entidad_financiera.repository.ClienteRepository;
import com.f2x.pruebatecnica.entidad_financiera.repository.ProductoRepository;
import com.f2x.pruebatecnica.entidad_financiera.util.mapper.ProductoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoMapper productoMapper;

    @Override
    public List<ProductoDto> obtenerTodosLosProductos() {
        return productoRepository.findAll()
                .stream().map(productoMapper::productoToProductoDto).toList();
    }

    @Override
    public ProductoDto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .map(productoMapper::productoToProductoDto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Transactional
    @Override
    public ProductoDto crearProducto(ProductoDto producto) {
        if (productoRepository.existsByNumeroCuenta(producto.getNumeroCuenta())) {
            throw new RuntimeException("El número de cuenta ya está en uso");
        }
        if (!clienteRepository.existsById(producto.getCliente().getId())) {
            throw new RuntimeException("El cliente no existe");
        }
        return productoMapper.productoToProductoDto(productoRepository.save(
                productoMapper.productoDtoToProducto(producto)));
    }

    @Transactional
    @Override
    public ProductoDto actualizarProducto(Long id, ProductoDto productoActualizado) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setEstado(productoActualizado.getEstado());
        producto.setSaldo(productoActualizado.getSaldo());
        producto.setExentaGmf(productoActualizado.getExentaGmf());
        producto.setFechaModificacion(Instant.now());
        return productoMapper.productoToProductoDto(productoRepository.save(producto));
    }

    @Transactional
    @Override
    public void eliminarProducto(Long id) {
        ProductoDto producto = obtenerProductoPorId(id);
        if (BigDecimal.ZERO.compareTo(BigDecimal.valueOf(producto.getSaldo())) != 0) {
            throw new RuntimeException("No se puede eliminar el producto porque tiene saldo diferente de cero");
        }
        productoRepository.deleteById(id);
    }
}
