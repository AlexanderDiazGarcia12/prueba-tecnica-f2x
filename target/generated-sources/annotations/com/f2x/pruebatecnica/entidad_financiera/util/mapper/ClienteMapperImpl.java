package com.f2x.pruebatecnica.entidad_financiera.util.mapper;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.ClienteDto;
import com.f2x.pruebatecnica.entidad_financiera.model.entity.Cliente;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-17T20:19:23-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Autowired
    private TipoIdentificacionMapper tipoIdentificacionMapper;

    @Override
    public Cliente clienteDtoToCliente(ClienteDto dto) {
        if ( dto == null ) {
            return null;
        }

        Cliente.ClienteBuilder cliente = Cliente.builder();

        cliente.id( dto.getId() );
        cliente.nombre( dto.getNombre() );
        cliente.apellidos( dto.getApellidos() );
        cliente.tipoIdentificacion( tipoIdentificacionMapper.tipoIdentificacionDtoToTipoIdentificacion( dto.getTipoIdentificacion() ) );
        cliente.numeroIdentificacion( dto.getNumeroIdentificacion() );
        cliente.correoElectronico( dto.getCorreoElectronico() );
        cliente.fechaNacimiento( dto.getFechaNacimiento() );
        cliente.fechaCreacion( dto.getFechaCreacion() );
        cliente.fechaModificacion( dto.getFechaModificacion() );

        return cliente.build();
    }

    @Override
    public ClienteDto clienteToClienteDto(Cliente entity) {
        if ( entity == null ) {
            return null;
        }

        ClienteDto.ClienteDtoBuilder clienteDto = ClienteDto.builder();

        clienteDto.id( entity.getId() );
        clienteDto.nombre( entity.getNombre() );
        clienteDto.apellidos( entity.getApellidos() );
        clienteDto.tipoIdentificacion( tipoIdentificacionMapper.tipoIdentificacionToTipoIdentificacionDto( entity.getTipoIdentificacion() ) );
        clienteDto.numeroIdentificacion( entity.getNumeroIdentificacion() );
        clienteDto.correoElectronico( entity.getCorreoElectronico() );
        clienteDto.fechaNacimiento( entity.getFechaNacimiento() );
        clienteDto.fechaCreacion( entity.getFechaCreacion() );
        clienteDto.fechaModificacion( entity.getFechaModificacion() );

        return clienteDto.build();
    }
}
