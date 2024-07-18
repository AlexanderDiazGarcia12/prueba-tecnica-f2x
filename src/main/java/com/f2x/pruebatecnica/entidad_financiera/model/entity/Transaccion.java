package com.f2x.pruebatecnica.entidad_financiera.model.entity;

import com.f2x.pruebatecnica.entidad_financiera.model.constant.TipoTransaccion;
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
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transaccion")
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoTransaccion tipoTransaccion;

    private Double monto;

    @UpdateTimestamp
    private Instant fechaTransaccion;

    @Column(nullable = true, length = 255)
    private String descripcion;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_origen_id")
    private Producto productoOrigen;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_destino_id")
    private Producto productoDestino;
}
