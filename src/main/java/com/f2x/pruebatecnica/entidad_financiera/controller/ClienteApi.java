package com.f2x.pruebatecnica.entidad_financiera.controller;


import com.f2x.pruebatecnica.entidad_financiera.model.dto.ClienteDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Validated
public interface ClienteApi {
    @Operation(summary = "Obtener todos los clientes", description = "", tags = {"clientes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClienteDto.class))))})
    @RequestMapping(value = "/clientes",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<ClienteDto>> clientesGet();


    @Operation(summary = "Eliminar un cliente", description = "", tags = {"clientes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado"),

            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")})
    @RequestMapping(value = "/clientes/{id}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> clientesIdDelete(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("id") Long id
    );


    @Operation(summary = "Obtener un cliente por ID", description = "", tags = {"clientes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDto.class))),

            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")})
    @RequestMapping(value = "/clientes/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<ClienteDto> clientesIdGet(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("id") Long id
    );


    @Operation(summary = "Actualizar un cliente", description = "", tags = {"clientes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDto.class))),

            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")})
    @RequestMapping(value = "/clientes/{id}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<ClienteDto> clientesIdPut(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("id") Long id
            , @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody ClienteDto body
    );


    @Operation(summary = "Crear un nuevo cliente", description = "", tags = {"clientes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDto.class)))})
    @RequestMapping(value = "/clientes",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<ClienteDto> clientesPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody ClienteDto body
    );
}
