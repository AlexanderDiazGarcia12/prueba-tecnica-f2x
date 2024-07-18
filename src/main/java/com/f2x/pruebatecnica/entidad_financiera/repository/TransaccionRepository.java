package com.f2x.pruebatecnica.entidad_financiera.repository;

import com.f2x.pruebatecnica.entidad_financiera.model.entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
}
