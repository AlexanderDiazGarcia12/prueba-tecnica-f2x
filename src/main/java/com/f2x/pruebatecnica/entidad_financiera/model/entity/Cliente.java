package com.f2x.pruebatecnica.entidad_financiera.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellidos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_identificacion_id", nullable = false)
    private TipoIdentificacion tipoIdentificacion;

    @Column(nullable = false, length = 12)
    private String numeroIdentificacion;

    @Column(nullable = false, unique = true, length = 100)
    private String correoElectronico;

    @Column(nullable = true)
    private Instant fechaNacimiento;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant fechaCreacion;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant fechaModificacion;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Producto> productos;

}
