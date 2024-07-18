package com.f2x.pruebatecnica.entidad_financiera.service.transaccion;

import com.f2x.pruebatecnica.entidad_financiera.model.constant.TipoTransaccion;
import org.springframework.stereotype.Component;

@Component
public class TransaccionFactory {
    public EjecutorTransaccion crearTransaccion(TipoTransaccion tipo) {
        return switch (tipo) {
            case TRANSFERENCIA -> new Transferencia();
            case CONSIGNACION -> new Consignacion();
            case RETIRO -> new Retiro();
            default -> throw new IllegalArgumentException("Tipo de transacción no soportado");
        };
    }
}
