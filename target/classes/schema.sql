CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Script de creación de la tabla para almacenar los tipos de identificación.
CREATE TABLE IF NOT EXISTS tipo_identificacion (
	id BIGSERIAL PRIMARY KEY,
	nombre VARCHAR(50) UNIQUE NOT NULL,
	codigo VARCHAR(5) UNIQUE NOT NULL,
	descripcion VARCHAR(255)
);

-- Script de creación de la tabla  para almacenar los clientes.
CREATE TABLE IF NOT EXISTS cliente (
	id BIGSERIAL PRIMARY KEY,
	tipo_identificacion_id BIGSERIAL NOT NULL,
	numero_identificacion VARCHAR(12) NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	apellidos VARCHAR(50) NOT NULL,
	correo_electronico VARCHAR(100) UNIQUE NOT NULL CHECK (correo_electronico ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
	password VARCHAR(255) NOT NULL CHECK (length(password) >= 60),
	fecha_nacimiento DATE,
	fecha_creacion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT fk_tipo_identificacion_id FOREIGN KEY (tipo_identificacion_id) REFERENCES tipo_identificacion(id) ON DELETE CASCADE
);

-- Script de creación de la tabla para almacenar los productos
CREATE TABLE IF NOT EXISTS producto (
	id BIGSERIAL PRIMARY KEY,
	tipo_cuenta VARCHAR(25) NOT NULL CHECK (tipo_cuenta IN ('AHORROS', 'CORRIENTE')),
	numero_cuenta VARCHAR(10) UNIQUE NOT NULL,
	estado VARCHAR(10) CHECK (estado IN ('ACTIVA', 'INACTIVA', 'CANCELADA')),
	saldo DECIMAL(15, 2) NOT NULL,
	exenta_gmf BOOLEAN DEFAULT FALSE,
	fecha_creacion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	fecha_modificacion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	cliente_id BIGSERIAL NOT NULL,
	CONSTRAINT fk_cliente_id FOREIGN KEY (cliente_id) REFERENCES cliente(id),
	CONSTRAINT ck_saldo_ahorros CHECK (tipo_cuenta != 'AHORROS' OR saldo >= 0)
);

-- Script de creación de la tabla para almacenar las transacciones
CREATE TABLE IF NOT EXISTS transaccion (
	id BIGSERIAL PRIMARY KEY,
	tipo_transaccion VARCHAR(20) CHECK (tipo_transaccion IN ('CONSIGNACION', 'RETIRO', 'TRANSFERENCIA')),
	monto DECIMAL(15, 2) NOT NULL,
	fecha_transaccion TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	descripcion VARCHAR(255),
	producto_origen_id BIGSERIAL NOT NULL,
	producto_destino_id BIGSERIAL,
	CONSTRAINT fk_producto_origen_id FOREIGN KEY (producto_origen_id) REFERENCES producto(id),
	CONSTRAINT fk_producto_destino_id FOREIGN KEY (producto_destino_id) REFERENCES producto(id)
);

-- Función para generar números de cuenta automáticamente
CREATE OR REPLACE FUNCTION generar_numero_cuenta()
RETURNS TRIGGER AS $$
BEGIN
	IF NEW.tipo_cuenta = 'AHORROS' THEN
		NEW.numero_cuenta := '53' || LPAD(nextval('producto_id_seq')::text, 8, '0');
		NEW.estado := 'ACTIVA';
	ELSEIF NEW.tipo_cuenta = 'CORRIENTE' THEN
		NEW.numero_cuenta := '33' || LPAD(nextval('producto_id_seq')::text, 8, '0');
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Creación de trigger para ejecutar la función que genera los números de cuenta automáticamente al insertar un nuevo producto
CREATE TRIGGER trigger_generar_numero_cuenta
BEFORE INSERT ON producto
FOR EACH ROW
EXECUTE FUNCTION generar_numero_cuenta();

-- Función para validar el saldo y evitar la cancelación de cuentas con saldo diferente de 0
CREATE OR REPLACE FUNCTION check_cancelacion_cuenta()
RETURN TRIGGER AS $$
BEGIN
	IF NEW.estado = 'CANCELADA' AND NEW.saldo <> 0 THEN
		RAISE EXCEPTION 'No se puede cancelar una cuenta con saldo diferente a 0';
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Creación de trigger para ejecutar la función que valida el saldo de una cuenta al actualizar su estado a Cancelada.
CREATE TRIGGER trigger_check_cancelation_cuenta
BEFORE UPDATE ON producto
FOR EACH ROW
EXECUTE FUNCTION check_cancelacion_cuenta();

-- Función para actualizar la fecha de modificación
CREATE OR REPLACE FUNCTION actualizar_fecha_modificacion()
RETURNS TRIGGER AS $$
BEGIN
	NEW.fecha_modificacion = NOW();
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Creación de trigger para ejecutar la función que actualiza la fecha de modificación de un cliente
CREATE TRIGGER trigger_actualizar_fecha_modificacion_cliente
BEFORE UPDATE ON cliente
FOR EACH ROW
EXECUTE FUNCTION actualizar_fecha_modificacion();

-- Creación de trigger para ejecutar la función que actualiza la fecha de modificación de un producto
CREATE TRIGGER trigger_actualizar_fecha_modificacion_producto
BEFORE UPDATE ON producto
FOR EACH ROW
EXECUTE FUNCTION actualizar_fecha_modificacion();

-- Creación de trigger para ejecutar la función que actualiza la fecha de transacción
CREATE TRIGGER trigger_actualizar_fecha_transaccion
BEFORE UPDATE ON transaccion
FOR EACH ROW
EXECUTE FUNCTION actualizar_fecha_modificacion();