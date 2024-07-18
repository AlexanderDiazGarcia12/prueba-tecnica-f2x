package com.f2x.pruebatecnica.entidad_financiera.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
public class ClienteDto {
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 50, message = "Los apellidos no pueden tener más de 50 caracteres")
    private String apellidos;

    @NotNull(message = "El tipo de identificación es obligatorio")
    private TipoIdentificacionDto tipoIdentificacion;

    @NotBlank(message = "El número de identificación es obligatorio")
    @Size(max = 12, message = "El número de identificación no puede tener más de 12 caracteres")
    private String numeroIdentificacion;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "El correo electrónico no es válido")
    @Size(max = 100, message = "El correo electrónico no puede tener más de 100 caracteres")
    private String correoElectronico;

    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private Instant fechaNacimiento;

    private Instant fechaCreacion;

    private Instant fechaModificacion;
}
