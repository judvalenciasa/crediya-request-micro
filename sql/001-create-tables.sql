-- Crear tabla de estados
CREATE TABLE IF NOT EXISTS estados (
    id_estado BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT
);

-- Crear tabla de tipo_prestamos
CREATE TABLE IF NOT EXISTS tipo_prestamos (
    id_tipo_prestamo BIGINT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    monto_minimo NUMERIC(15,2),
    monto_maximo NUMERIC(15,2),
    tasa_interes NUMERIC(5,4),
    validacion_automatica BOOLEAN
);

-- Crear tabla de solicitudes
CREATE TABLE IF NOT EXISTS solicitudes (
    id_solicitud BIGINT PRIMARY KEY,
    monto NUMERIC(15,2) NOT NULL,
    plazo INTEGER NOT NULL,
    id_estado INTEGER NOT NULL,
    id_tipo_prestamo INTEGER NOT NULL,
    documento_identidad VARCHAR(255) NOT NULL,
    CONSTRAINT fk_solicitud_estado FOREIGN KEY (id_estado) REFERENCES estados(id_estado),
    CONSTRAINT fk_solicitud_tipo FOREIGN KEY (id_tipo_prestamo) REFERENCES tipo_prestamos(id_tipo_prestamo)
);

-- Crear índices para mejorar rendimiento
CREATE INDEX IF NOT EXISTS idx_solicitudes_estado ON solicitudes(id_estado);
CREATE INDEX IF NOT EXISTS idx_solicitudes_tipo ON solicitudes(id_tipo_prestamo);
CREATE INDEX IF NOT EXISTS idx_solicitudes_documento ON solicitudes(documento_identidad);