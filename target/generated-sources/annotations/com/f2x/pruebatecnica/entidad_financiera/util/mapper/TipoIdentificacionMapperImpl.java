package com.f2x.pruebatecnica.entidad_financiera.util.mapper;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.TipoIdentificacionDto;
import com.f2x.pruebatecnica.entidad_financiera.model.entity.TipoIdentificacion;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-17T20:19:23-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class TipoIdentificacionMapperImpl implements TipoIdentificacionMapper {

    @Override
    public TipoIdentificacion tipoIdentificacionDtoToTipoIdentificacion(TipoIdentificacionDto dto) {
        if ( dto == null ) {
            return null;
        }

        TipoIdentificacion.TipoIdentificacionBuilder tipoIdentificacion = TipoIdentificacion.builder();

        tipoIdentificacion.id( dto.getId() );
        tipoIdentificacion.nombre( dto.getNombre() );
        tipoIdentificacion.codigo( dto.getCodigo() );
        tipoIdentificacion.descripcion( dto.getDescripcion() );

        return tipoIdentificacion.build();
    }

    @Override
    public TipoIdentificacionDto tipoIdentificacionToTipoIdentificacionDto(TipoIdentificacion entity) {
        if ( entity == null ) {
            return null;
        }

        TipoIdentificacionDto.TipoIdentificacionDtoBuilder tipoIdentificacionDto = TipoIdentificacionDto.builder();

        tipoIdentificacionDto.id( entity.getId() );
        tipoIdentificacionDto.nombre( entity.getNombre() );
        tipoIdentificacionDto.codigo( entity.getCodigo() );
        tipoIdentificacionDto.descripcion( entity.getDescripcion() );

        return tipoIdentificacionDto.build();
    }
}
