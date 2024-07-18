package com.f2x.pruebatecnica.entidad_financiera.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoIdentificacionDto {
    @NotNull(message = "El tipo de indentifiación debe estar registrado.")
    private Long id;
    @NotBlank(message = "El nombre del tipo de identificación es obligatorio")
    @Size(max = 50, message = "El nombre del tipo de indentificación no puede tener más de 50 caracteres")
    private String nombre;
    private String codigo;
    private String descripcion;
}
