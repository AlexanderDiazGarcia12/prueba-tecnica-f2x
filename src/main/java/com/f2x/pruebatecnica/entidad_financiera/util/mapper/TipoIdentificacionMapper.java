package com.f2x.pruebatecnica.entidad_financiera.util.mapper;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.TipoIdentificacionDto;
import com.f2x.pruebatecnica.entidad_financiera.model.entity.TipoIdentificacion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TipoIdentificacionMapper {

    TipoIdentificacionMapper INSTANCE = Mappers.getMapper(TipoIdentificacionMapper.class);

    TipoIdentificacion tipoIdentificacionDtoToTipoIdentificacion(TipoIdentificacionDto dto);
    TipoIdentificacionDto tipoIdentificacionToTipoIdentificacionDto(TipoIdentificacion entity);

}
