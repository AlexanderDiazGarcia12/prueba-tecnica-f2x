package com.f2x.pruebatecnica.entidad_financiera.controller;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.TransaccionDto;
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
public interface TransaccionApi {
    @Operation(summary = "Obtener todas las transacciones", description = "", tags = {"transacciones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de transacciones", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TransaccionDto.class))))})
    @RequestMapping(value = "/transacciones",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<TransaccionDto>> transaccionesGet();


    @Operation(summary = "Eliminar una transacción", description = "", tags = {"transacciones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transacción eliminada"),

            @ApiResponse(responseCode = "404", description = "Transacción no encontrada")})
    @RequestMapping(value = "/transacciones/{id}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> transaccionesIdDelete(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("id") Long id
    );


    @Operation(summary = "Obtener una transacción por ID", description = "", tags = {"transacciones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transacción encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransaccionDto.class))),

            @ApiResponse(responseCode = "404", description = "Transacción no encontrada")})
    @RequestMapping(value = "/transacciones/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<TransaccionDto> transaccionesIdGet(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("id") Long id
    );


    @Operation(summary = "Actualizar una transacción", description = "", tags = {"transacciones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transacción actualizada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransaccionDto.class))),

            @ApiResponse(responseCode = "404", description = "Transacción no encontrada")})
    @RequestMapping(value = "/transacciones/{id}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<TransaccionDto> transaccionesIdPut(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("id") Long id
            , @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody TransaccionDto body
    );


    @Operation(summary = "Crear una nueva transacción", description = "", tags = {"transacciones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transacción creada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransaccionDto.class)))})
    @RequestMapping(value = "/transacciones",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<TransaccionDto> transaccionesPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody TransaccionDto body
    );
}
