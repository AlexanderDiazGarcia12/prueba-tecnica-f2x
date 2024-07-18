package com.f2x.pruebatecnica.entidad_financiera.service;

import com.f2x.pruebatecnica.entidad_financiera.model.constant.TipoTransaccion;
import com.f2x.pruebatecnica.entidad_financiera.model.dto.ProductoDto;
import com.f2x.pruebatecnica.entidad_financiera.model.dto.TransaccionDto;
import com.f2x.pruebatecnica.entidad_financiera.model.entity.Transaccion;
import com.f2x.pruebatecnica.entidad_financiera.repository.TransaccionRepository;
import com.f2x.pruebatecnica.entidad_financiera.service.transaccion.EjecutorTransaccion;
import com.f2x.pruebatecnica.entidad_financiera.service.transaccion.TransaccionFactory;
import com.f2x.pruebatecnica.entidad_financiera.util.mapper.TransaccionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransaccionServiceImpl implements TransaccionService {
    private final ProductoService productoService;
    private final TransaccionRepository transaccionRepository;
    private final TransaccionMapper transaccionMapper;
    private final TransaccionFactory transaccionFactory;

    @Override
    public List<TransaccionDto> obtenerTodasLasTransacciones() {
        return transaccionRepository.findAll()
                .stream()
                .map(transaccionMapper::transaccionToTransaccionDto)
                .toList();
    }

    @Override
    public TransaccionDto obtenerTransaccionPorId(Long id) {
        return transaccionRepository.findById(id)
                .map(transaccionMapper::transaccionToTransaccionDto)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
    }

    @Transactional
    @Override
    public TransaccionDto crearTransaccion(TransaccionDto transaccionDto) {
        ProductoDto productoOrigen = productoService.obtenerProductoPorId(transaccionDto.getProductoOrigen().getId());

        ProductoDto productoDestino = null;
        if (transaccionDto.getTipoTransaccion().equals(TipoTransaccion.TRANSFERENCIA)) {
            productoDestino = productoService.obtenerProductoPorId(transaccionDto.getProductoDestino().getId());
        }

        EjecutorTransaccion transaccion = transaccionFactory.crearTransaccion(transaccionDto.getTipoTransaccion());
        transaccion.ejecutar(productoOrigen, productoDestino, BigDecimal.valueOf(transaccionDto.getMonto()));

        productoService.actualizarProducto(productoOrigen.getId(), productoOrigen);
        if (productoDestino != null) {
            productoService.actualizarProducto(productoDestino.getId(), productoDestino);
        }

        Transaccion transaccionEntity = transaccionMapper.transaccionDtoToTransaccion(transaccionDto);
        return transaccionMapper.transaccionToTransaccionDto(transaccionRepository.save(transaccionEntity));
    }

    @Transactional
    @Override
    public TransaccionDto actualizarTransaccion(Long id, TransaccionDto transaccionActualizada) {
        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
        transaccion.setMonto(transaccionActualizada.getMonto());
        transaccion.setDescripcion(transaccionActualizada.getDescripcion());
        return transaccionMapper.transaccionToTransaccionDto(transaccionRepository.save(transaccion));
    }

    @Transactional
    @Override
    public void eliminarTransaccion(Long id) {
        transaccionRepository.deleteById(id);
    }


}
