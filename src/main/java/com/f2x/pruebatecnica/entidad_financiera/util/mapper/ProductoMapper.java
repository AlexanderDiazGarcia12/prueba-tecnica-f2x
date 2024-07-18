package com.f2x.pruebatecnica.entidad_financiera.util.mapper;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.ProductoDto;
import com.f2x.pruebatecnica.entidad_financiera.model.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface ProductoMapper {

    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

    Producto productoDtoToProducto(ProductoDto dto);
    ProductoDto productoToProductoDto(Producto entity);

}
