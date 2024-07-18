package com.f2x.pruebatecnica.entidad_financiera.service;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.ProductoDto;

import java.util.List;

public interface ProductoService {
    List<ProductoDto> obtenerTodosLosProductos();
    ProductoDto obtenerProductoPorId(Long id);
    ProductoDto crearProducto(ProductoDto producto);
    ProductoDto actualizarProducto(Long id, ProductoDto productoActualizado);
    void eliminarProducto(Long id);
}
