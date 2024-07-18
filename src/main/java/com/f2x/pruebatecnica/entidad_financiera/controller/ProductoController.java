package com.f2x.pruebatecnica.entidad_financiera.controller;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.ProductoDto;
import com.f2x.pruebatecnica.entidad_financiera.service.ProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductoController implements ProductoApi {

    private final ProductoService productoService;

    @Override
    public ResponseEntity<List<ProductoDto>> productosGet() {
        return ResponseEntity.ok(productoService.obtenerTodosLosProductos());
    }

    @Override
    public ResponseEntity<Void> productosIdDelete(Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ProductoDto> productosIdGet(Long id) {
        return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
    }

    @Override
    public ResponseEntity<ProductoDto> productosIdPut(Long id, ProductoDto body) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, body));
    }

    @Override
    public ResponseEntity<ProductoDto> productosPost(ProductoDto body) {
        return ResponseEntity.ok(productoService.crearProducto(body));
    }
}
