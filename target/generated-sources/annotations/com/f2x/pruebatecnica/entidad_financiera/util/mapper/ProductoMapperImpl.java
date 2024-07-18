package com.f2x.pruebatecnica.entidad_financiera.util.mapper;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.ProductoDto;
import com.f2x.pruebatecnica.entidad_financiera.model.entity.Producto;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-17T21:26:52-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class ProductoMapperImpl implements ProductoMapper {

    @Autowired
    private ClienteMapper clienteMapper;

    @Override
    public Producto productoDtoToProducto(ProductoDto dto) {
        if ( dto == null ) {
            return null;
        }

        Producto.ProductoBuilder producto = Producto.builder();

        producto.id( dto.getId() );
        producto.tipoCuenta( dto.getTipoCuenta() );
        producto.numeroCuenta( dto.getNumeroCuenta() );
        producto.estado( dto.getEstado() );
        producto.saldo( dto.getSaldo() );
        producto.exentaGmf( dto.getExentaGmf() );
        producto.fechaCreacion( dto.getFechaCreacion() );
        producto.fechaModificacion( dto.getFechaModificacion() );
        producto.cliente( clienteMapper.clienteDtoToCliente( dto.getCliente() ) );

        return producto.build();
    }

    @Override
    public ProductoDto productoToProductoDto(Producto entity) {
        if ( entity == null ) {
            return null;
        }

        ProductoDto.ProductoDtoBuilder productoDto = ProductoDto.builder();

        productoDto.id( entity.getId() );
        productoDto.tipoCuenta( entity.getTipoCuenta() );
        productoDto.numeroCuenta( entity.getNumeroCuenta() );
        productoDto.estado( entity.getEstado() );
        productoDto.saldo( entity.getSaldo() );
        productoDto.exentaGmf( entity.getExentaGmf() );
        productoDto.fechaCreacion( entity.getFechaCreacion() );
        productoDto.fechaModificacion( entity.getFechaModificacion() );
        productoDto.cliente( clienteMapper.clienteToClienteDto( entity.getCliente() ) );

        return productoDto.build();
    }
}
