package com.f2x.pruebatecnica.entidad_financiera.util.mapper;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.TransaccionDto;
import com.f2x.pruebatecnica.entidad_financiera.model.entity.Transaccion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ProductoMapper.class})
public interface TransaccionMapper {

    TransaccionMapper INSTANCE = Mappers.getMapper(TransaccionMapper.class);

    Transaccion transaccionDtoToTransaccion(TransaccionDto dto);
    TransaccionDto transaccionToTransaccionDto(Transaccion entity);

}
