package com.f2x.pruebatecnica.entidad_financiera.util.mapper;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.ClienteDto;
import com.f2x.pruebatecnica.entidad_financiera.model.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ProductoMapper.class, TipoIdentificacionMapper.class})
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    Cliente clienteDtoToCliente(ClienteDto dto);
    ClienteDto clienteToClienteDto(Cliente entity);
}
