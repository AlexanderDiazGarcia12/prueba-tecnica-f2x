package com.f2x.pruebatecnica.entidad_financiera.model.entity;

import com.f2x.pruebatecnica.entidad_financiera.model.constant.EstadoCuenta;
import com.f2x.pruebatecnica.entidad_financiera.model.constant.TipoCuenta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCuenta tipoCuenta;

    @Column(nullable = true)
    private String numeroCuenta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private EstadoCuenta estado;

    @Column(nullable = true)
    private Double saldo;

    @Column(nullable = true)
    private Boolean exentaGmf;

    @CreationTimestamp
    private Instant fechaCreacion;

    @UpdateTimestamp
    private Instant fechaModificacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    @ToString.Exclude
    private Cliente cliente;
}
