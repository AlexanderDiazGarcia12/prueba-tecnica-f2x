package com.f2x.pruebatecnica.entidad_financiera.repository;

import com.f2x.pruebatecnica.entidad_financiera.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByCorreoElectronico(String correoElectronico);
    boolean existsByIdAndProductosIsNotEmpty(Long id);
}
