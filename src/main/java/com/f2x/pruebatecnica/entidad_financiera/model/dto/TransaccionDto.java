package com.f2x.pruebatecnica.entidad_financiera.model.dto;


import com.f2x.pruebatecnica.entidad_financiera.model.constant.TipoTransaccion;
import com.f2x.pruebatecnica.entidad_financiera.util.validador.ValidadorProductoDestino;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ValidadorProductoDestino
public class TransaccionDto {
    private Long id;

    @NotNull(message = "El tipo de transacción es obligatorio")
    private TipoTransaccion tipoTransaccion;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    @Digits(integer = 13, fraction = 2, message = "El monto debe tener como máximo 13 dígitos enteros y 2 decimales")
    private Double monto;

    private Instant fechaTransaccion;

    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    private String descripcion;

    @NotNull(message = "El producto de origen es obligatorio")
    private ProductoDto productoOrigen;

    private ProductoDto productoDestino;
}
