package com.f2x.pruebatecnica.entidad_financiera.controller;

import com.f2x.pruebatecnica.entidad_financiera.model.dto.ProductoDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface ProductoApi {
    @Operation(summary = "Obtener todos los productos", description = "", tags = {"produtos"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductoDto.class))))})
    @RequestMapping(value = "/productos",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<ProductoDto>> productosGet();


    @Operation(summary = "Eliminar un producto", description = "", tags = {"produtos"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado"),

            @ApiResponse(responseCode = "404", description = "Producto no encontrado")})
    @RequestMapping(value = "/productos/{id}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> productosIdDelete(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("id") Long id
    );


    @Operation(summary = "Obtener un producto por ID", description = "", tags = {"produtos"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDto.class))),

            @ApiResponse(responseCode = "404", description = "Producto no encontrado")})
    @RequestMapping(value = "/productos/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<ProductoDto> productosIdGet(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("id") Long id
    );


    @Operation(summary = "Actualizar un producto", description = "", tags = {"produtos"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDto.class))),

            @ApiResponse(responseCode = "404", description = "Producto no encontrado")})
    @RequestMapping(value = "/productos/{id}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<ProductoDto> productosIdPut(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("id") Long id
            , @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody ProductoDto body
    );


    @Operation(summary = "Crear un nuevo producto", description = "", tags = {"produtos"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDto.class)))})
    @RequestMapping(value = "/productos",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<ProductoDto> productosPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody ProductoDto body
    );
}
