package com.f2x.pruebatecnica.entidad_financiera.util.mapper;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.TransaccionDto;
import com.f2x.pruebatecnica.entidad_financiera.model.entity.Transaccion;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-17T20:19:23-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class TransaccionMapperImpl implements TransaccionMapper {

    @Autowired
    private ProductoMapper productoMapper;

    @Override
    public Transaccion transaccionDtoToTransaccion(TransaccionDto dto) {
        if ( dto == null ) {
            return null;
        }

        Transaccion.TransaccionBuilder transaccion = Transaccion.builder();

        transaccion.id( dto.getId() );
        transaccion.tipoTransaccion( dto.getTipoTransaccion() );
        transaccion.monto( dto.getMonto() );
        transaccion.fechaTransaccion( dto.getFechaTransaccion() );
        transaccion.descripcion( dto.getDescripcion() );
        transaccion.productoOrigen( productoMapper.productoDtoToProducto( dto.getProductoOrigen() ) );
        transaccion.productoDestino( productoMapper.productoDtoToProducto( dto.getProductoDestino() ) );

        return transaccion.build();
    }

    @Override
    public TransaccionDto transaccionToTransaccionDto(Transaccion entity) {
        if ( entity == null ) {
            return null;
        }

        TransaccionDto.TransaccionDtoBuilder transaccionDto = TransaccionDto.builder();

        transaccionDto.id( entity.getId() );
        transaccionDto.tipoTransaccion( entity.getTipoTransaccion() );
        transaccionDto.monto( entity.getMonto() );
        transaccionDto.fechaTransaccion( entity.getFechaTransaccion() );
        transaccionDto.descripcion( entity.getDescripcion() );
        transaccionDto.productoOrigen( productoMapper.productoToProductoDto( entity.getProductoOrigen() ) );
        transaccionDto.productoDestino( productoMapper.productoToProductoDto( entity.getProductoDestino() ) );

        return transaccionDto.build();
    }
}
