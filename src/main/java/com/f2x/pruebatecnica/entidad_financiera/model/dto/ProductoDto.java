package com.f2x.pruebatecnica.entidad_financiera.model.dto;

import com.f2x.pruebatecnica.entidad_financiera.model.constant.EstadoCuenta;
import com.f2x.pruebatecnica.entidad_financiera.model.constant.TipoCuenta;
import com.f2x.pruebatecnica.entidad_financiera.util.validador.GrupoValidacionCuentaAhorros;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDto {
    private Long id;

    @NotNull(message = "El tipo de cuenta es obligatorio")
    private TipoCuenta tipoCuenta;

    @NotBlank(message = "El número de cuenta es obligatorio")
    @Size(min = 10, max = 10, message = "El número de cuenta debe tener exactamente 10 caracteres")
    @Pattern(regexp = "^[0-9]+$", message = "El número de cuenta debe contener solo dígitos")
    private String numeroCuenta;

    @NotNull(message = "El estado de la cuenta es obligatorio")
    private EstadoCuenta estado;

    @NotNull(message = "El saldo es obligatorio")
    @DecimalMin(value = "0.0", message = "El saldo no puede ser negativo para cuentas de ahorro", groups = GrupoValidacionCuentaAhorros.class)
    private Double saldo;

    private Boolean exentaGmf;

    private Instant fechaCreacion;

    private Instant fechaModificacion;

    @NotNull(message = "El cliente es obligatorio")
    private ClienteDto cliente;
}
