package com.f2x.pruebatecnica.entidad_financiera.repository;

import com.f2x.pruebatecnica.entidad_financiera.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    boolean existsByNumeroCuenta(String numeroCuenta);
    boolean existsByClienteId(Long clienteId);
}
